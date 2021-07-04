package ir.maktab.homeservices.service.specialistService;

import ir.maktab.homeservices.configuration.discountProperty.RetunedMoney;
import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.data.repository.specialist.SpecialistRepository;
import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.dto.SpecialistSignUpDto;
import ir.maktab.homeservices.exceptions.checkes.DuplicateEmailException;
import ir.maktab.homeservices.exceptions.checkes.DuplicateUsernameException;
import ir.maktab.homeservices.exceptions.checkes.PasswordNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.userService.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final Mapper mapper;
    private final MaktabMessageSource maktabMessageSource;
    private final RetunedMoney retunedMoney;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository, Mapper mapper, MaktabMessageSource maktabMessageSource, RetunedMoney retunedMoney, PasswordEncoder passwordEncoder, UserService userService) {
        this.specialistRepository = specialistRepository;
        this.mapper = mapper;
        this.maktabMessageSource = maktabMessageSource;
        this.retunedMoney = retunedMoney;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public void delete(Integer id) {
        specialistRepository.deleteById(id);
    }

    @Override
    public SpecialistDto registerSpecialist(SpecialistSignUpDto specialistSignUpDto, String siteURL) throws UnsupportedEncodingException, MessagingException, DuplicateEmailException, DuplicateUsernameException {
        SpecialistDto specialistDto = new SpecialistDto()
                .setName(specialistSignUpDto.getName())
                .setEmail(specialistSignUpDto.getEmail())
                .setLastName(specialistSignUpDto.getLastName())
                .setUsername(specialistSignUpDto.getUsername())
                .setPassword(specialistSignUpDto.getPassword())
                .setProfilePicture(specialistSignUpDto.getProfilePicture());

        findDuplicateEmail(specialistDto.getEmail());
        findDuplicateUsername(specialistDto.getUsername());

        //TODO:: method
        String encodedPassword = passwordEncoder.encode(specialistDto.getPassword());
        specialistDto.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        specialistDto.setVerificationCode(randomCode).setEnabled(false).setUserStatus(UserStatus.NEW);
        Specialist specialist = specialistRepository.save(mapper.toSpecialist(specialistDto));
        specialist.getServiceCategoryList().add(mapper.toServiceCategory(specialistSignUpDto.getServiceCategory()));
        specialistRepository.save(specialist);

        userService.sendVerificationEmail(specialistDto, siteURL);
        return specialistDto;

    }


    @Override
    public void findDuplicateEmail(String email) throws DuplicateEmailException {
        Optional<Specialist> specialist = specialistRepository.findByEmail(email);
        if (specialist.isPresent())
            throw new DuplicateEmailException(maktabMessageSource.getEnglish("duplicate.email"));

    }

    @Override
    public void findDuplicateUsername(String username) throws DuplicateUsernameException {
        Optional<Specialist> specialist = specialistRepository.findByUsername(username);
        if (specialist.isPresent())
            throw new DuplicateUsernameException(maktabMessageSource.getEnglish("duplicate.username"));

    }


    @Override
    public void update(SpecialistDto specialistDto) throws SpecialistNotFoundException {
        if (specialistRepository.findById(specialistDto.getId()).isPresent()) {
            specialistRepository.save(mapper.toSpecialist(specialistDto));
        } else
            throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.not.found", new Object[]{specialistDto.getId()}));
    }

    @Transactional
    @Override
    public SpecialistDto login(SpecialistDto specialistDto) throws SpecialistNotFoundException {
        Optional<Specialist> byUsernameAndPassword = specialistRepository.findByUsernameAndPassword(specialistDto.getUsername(), specialistDto.getPassword());
        if (byUsernameAndPassword.isPresent())
            return mapper.toSpecialistDto(byUsernameAndPassword.get());
        throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("login.specialist.not.found", new Object[]{specialistDto.getUsername()}));
    }

    @Transactional
    @Override
    public void updateRate(SpecialistDto specialistDto, Double score) {
        double rate = specialistDto.getRate();
        Integer counter;
        if (rate == 0) {
            counter = specialistDto.getCommentCounter() + 1;
            specialistRepository.updateRateAndCommentCounter(specialistDto.getId(), score, counter);
        } else {
            counter = specialistDto.getCommentCounter();
            Integer newCounter = ++counter;
            double newRate = ((counter * rate) + score) / (newCounter);
            specialistRepository.updateRateAndCommentCounter(specialistDto.getId(), newRate, newCounter);
        }
    }

    @Transactional
    @Override
    public void returnMoney(double price, SpecialistDto specialistDto) {
        double money = price * retunedMoney.getDiscount();
        double balance = specialistDto.getBalance() + money;
        specialistRepository.updateBalance(specialistDto.getId(), balance);
    }

    @Override
    public void updateServiceCategory(Integer specialistId, List<ServiceCategoryDto> serviceCategoryDtoList) {
        Set<ServiceCategory> serviceCategoryList = serviceCategoryDtoList.stream().map(mapper::toServiceCategory).collect(Collectors.toSet());
//        specialistRepository.updateService(specialistId,serviceCategoryList);
    }

    @Override
    public SpecialistDto findByUsername(String username) throws SpecialistNotFoundException {
        Optional<Specialist> byUsername = specialistRepository.findByUsername(username);
        if (byUsername.isPresent())
            return mapper.toSpecialistDto(byUsername.get());
        throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.not.found", new Object[]{username}));
    }

    @Override
    public SpecialistDto findById(Integer id) throws SpecialistNotFoundException {
        Optional<Specialist> byUsername = specialistRepository.findById(id);
        if (byUsername.isPresent())
            return mapper.toSpecialistDto(byUsername.get());
        throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.id.not.found", new Object[]{id}));


    }

    @Override
    public List<SpecialistDto> findAll() {
        List<Specialist> specialistList = specialistRepository.findAll();
        return specialistList.stream().map(mapper::toSpecialistDto).collect(Collectors.toList());
    }

    @Override
    public void changePassword(SpecialistDto specialistDto, String oldPass, String newPass) throws PasswordNotFoundException {
        userService.checkForChangePassword(specialistDto, oldPass, newPass);
    }

    @Override
    public boolean checkforStatus(SpecialistDto dto) {
        if (dto.getUserStatus().equals(UserStatus.APPROVE))
            return true;
        return false;
    }

    @Override
    public List<SpecialistDto> findApproveSpecialist() {
        List<Specialist> all = specialistRepository.findAll();
        all.removeIf(specialist -> specialist.getUserStatus().equals(UserStatus.WAITING));
        return all.stream().map(mapper::toSpecialistDto).collect(Collectors.toList());
    }


}
