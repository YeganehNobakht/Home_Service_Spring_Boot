package ir.maktab.homeService.service.specialistService;

import ir.maktab.homeService.dto.ServiceCategoryDto;
import ir.maktab.homeService.dto.SpecialistDto;
import ir.maktab.homeService.dto.SpecialistSignUpDto;
import ir.maktab.homeService.exceptions.checkes.DuplicateEmailException;
import ir.maktab.homeService.exceptions.checkes.DuplicateUsernameException;
import ir.maktab.homeService.exceptions.checkes.SpecialistNotFoundException;

import java.util.List;

public interface SpecialistService {

    SpecialistDto create(SpecialistSignUpDto specialistDto) throws DuplicateUsernameException, DuplicateEmailException;

    void delete(Integer id);

//    void changePassword(String username, String oldPass, String newPass) throws Exception;

    void update(SpecialistDto specialistDto) throws SpecialistNotFoundException;

//    SpecialistDto get(String username) throws Exception;

    SpecialistDto login(SpecialistDto specialistDto) throws SpecialistNotFoundException;

    void updateRate(SpecialistDto specialistDto, Double score);

    void returnMoney(double price, SpecialistDto specialistDto);

    void updateServiceCategory(Integer id, List<ServiceCategoryDto> serviceCategoryDtoList);
}
