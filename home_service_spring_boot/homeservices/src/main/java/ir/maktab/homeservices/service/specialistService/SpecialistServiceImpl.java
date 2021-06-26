package ir.maktab.homeservices.service.specialistService;

import ir.maktab.homeservices.configuration.discountProperty.RetunedMoney;
import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.data.repository.specialist.SpecialistRepository;
import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.dto.SpecialistSignUpDto;
import ir.maktab.homeservices.dto.UserFilter;
import ir.maktab.homeservices.exceptions.checkes.DuplicateEmailException;
import ir.maktab.homeservices.exceptions.checkes.DuplicateUsernameException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.userService.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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


    @Transactional
    @Override
    public SpecialistDto create(SpecialistSignUpDto specialistSignUpDto) throws DuplicateUsernameException, DuplicateEmailException {
        SpecialistDto specialistDto = new SpecialistDto()
                .setName(specialistSignUpDto.getName())
                .setEmail(specialistSignUpDto.getEmail())
                .setLastName(specialistSignUpDto.getLastName())
                .setUsername(specialistSignUpDto.getUsername())
                .setPassword(specialistSignUpDto.getPassword())
                .setProfilePicture(specialistSignUpDto.getProfilePicture())
                .setUserStatus(UserStatus.WAITING);



        Optional<Specialist> specialist1 = specialistRepository.findByUsername(specialistDto.getUsername());
        if (specialist1.isPresent()) {
            throw new DuplicateUsernameException(maktabMessageSource.getEnglish("duplicate.username"));
        } else {
            Optional<Specialist> specialist2 = specialistRepository.findByEmail(specialistDto.getEmail());
            if (specialist2.isPresent()) {
                throw new DuplicateEmailException(maktabMessageSource.getEnglish("duplicate.email"));
            } else {
                Specialist specialist = specialistRepository.save(mapper.toSpecialist(specialistDto));
                specialist.getServiceCategoryList().add(mapper.toServiceCategory(specialistSignUpDto.getServiceCategory()));
                specialistRepository.save(specialist);
                return mapper.toSpecialistDto(specialist);
            }
        }
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

        Optional<Specialist> specialist1 = specialistRepository.findByUsername(specialistDto.getUsername());
        if (specialist1.isPresent()) {
            throw new DuplicateUsernameException(maktabMessageSource.getEnglish("duplicate.username"));
        } else {
            Optional<Specialist> specialist2 = specialistRepository.findByEmail(specialistDto.getEmail());
            if (specialist2.isPresent()) {
                throw new DuplicateEmailException(maktabMessageSource.getEnglish("duplicate.email"));
            } else {
                String encodedPassword = passwordEncoder.encode(specialistDto.getPassword());
                specialistDto.setPassword(encodedPassword);

                String randomCode = RandomString.make(64);
                specialistDto.setVerificationCode(randomCode);
                specialistDto.setEnabled(false);

                specialistRepository.save(mapper.toSpecialist(specialistDto));
                userService.sendVerificationEmail(specialistDto, siteURL);
                return specialistDto;
            }
        }
    }

    @Override
    public void update(SpecialistDto specialistDto) throws SpecialistNotFoundException {
        if (specialistRepository.findById(specialistDto.getId()).isPresent()) {
            //using save method for update
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
        if (rate==0){
            counter = specialistDto.getCommentCounter()+1;
            specialistRepository.updateRateAndCommentCounter(specialistDto.getId(),score,counter);
        }
        else {
            counter = specialistDto.getCommentCounter();
            Integer newCounter = ++counter;
            double newRate = ((counter * rate) + score) / (newCounter);
            specialistRepository.updateRateAndCommentCounter(specialistDto.getId(),newRate,newCounter);
        }
    }

    @Transactional
    @Override
    public void returnMoney(double price, SpecialistDto specialistDto) {
            double money = price*retunedMoney.getDiscount();
            double balance = specialistDto.getBalance()+money;
            specialistRepository.updateBalance(specialistDto.getId(),balance);
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



}
