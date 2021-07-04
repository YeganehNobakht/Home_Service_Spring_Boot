package ir.maktab.homeservices.service.specialistService;

import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.dto.SpecialistSignUpDto;
import ir.maktab.homeservices.exceptions.checkes.DuplicateEmailException;
import ir.maktab.homeservices.exceptions.checkes.DuplicateUsernameException;
import ir.maktab.homeservices.exceptions.checkes.PasswordNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface SpecialistService {

    void delete(Integer id);

    SpecialistDto registerSpecialist(SpecialistSignUpDto specialistSignUpDto, String siteURL) throws UnsupportedEncodingException, MessagingException, DuplicateEmailException, DuplicateUsernameException;

    void findDuplicateEmail(String email) throws DuplicateEmailException;

    void findDuplicateUsername(String username) throws DuplicateUsernameException;

    void update(SpecialistDto specialistDto) throws SpecialistNotFoundException;

    SpecialistDto login(SpecialistDto specialistDto) throws SpecialistNotFoundException;

    void updateRate(SpecialistDto specialistDto, Double score);

    void returnMoney(double price, SpecialistDto specialistDto);

    void updateServiceCategory(Integer id, List<ServiceCategoryDto> serviceCategoryDtoList);

    SpecialistDto findByUsername(String username) throws SpecialistNotFoundException;

    SpecialistDto findById(Integer id) throws SpecialistNotFoundException;

    List<SpecialistDto> findAll();

    void changePassword(SpecialistDto specialistDto, String oldPass, String newPass) throws PasswordNotFoundException, SpecialistNotFoundException;

    boolean checkforStatus(SpecialistDto dto);

    List<SpecialistDto> findApproveSpecialist();
}
