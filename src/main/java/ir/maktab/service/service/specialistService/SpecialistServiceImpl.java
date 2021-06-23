package ir.maktab.service.service.specialistService;

import ir.maktab.service.configuration.discountProperty.RetunedMoney;
import ir.maktab.service.data.entity.ServiceCategory;
import ir.maktab.service.data.entity.Specialist;
import ir.maktab.service.data.entity.enums.UserStatus;
import ir.maktab.service.data.repository.specialist.SpecialistRepository;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SpecialistDto;
import ir.maktab.service.dto.SpecialistSignUpDto;
import ir.maktab.service.exceptions.checkes.DuplicateEmailException;
import ir.maktab.service.exceptions.checkes.DuplicateUsernameException;
import ir.maktab.service.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public SpecialistServiceImpl(SpecialistRepository specialistRepository, Mapper mapper, MaktabMessageSource maktabMessageSource, RetunedMoney retunedMoney) {
        this.specialistRepository = specialistRepository;
        this.mapper = mapper;
        this.maktabMessageSource = maktabMessageSource;
        this.retunedMoney = retunedMoney;
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

//    @Override
//    public void changePassword(String username, String oldPass, String newPass) throws Exception {
//        Optional<Specialist> specialist = specialistRepository.findById(username);
//        if (specialist.isPresent()) {
//            if (specialist.get().getPassword().equals(oldPass)) {
//                if (validations.validatePassword(newPass)) {
//                    specialist.get().setPassword(newPass);
//                    //using save method for update
//                    specialistRepository.save(specialist.get());
//                }
//            }
//        }
//    }

    @Override
    public void update(SpecialistDto specialistDto) throws SpecialistNotFoundException {
        if (specialistRepository.findById(specialistDto.getId()).isPresent()) {
            //using save method for update
            specialistRepository.save(mapper.toSpecialist(specialistDto));
        } else
            throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.not.found", new Object[]{specialistDto.getId()}));
    }

//    @Override
//    public SpecialistDto get(String username) throws Exception {
//        Optional<Specialist> specialist = specialistRepository.findById(username);
//        if (specialist.isPresent())
//            return mapper.toSpecialistDto(specialist.get());
//        else
//            throw new Exception("No such specialist found");
//
//    }

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


}
