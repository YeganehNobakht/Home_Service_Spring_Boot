package ir.maktab.homeService.service.managerService;


import ir.maktab.homeService.dto.*;
import ir.maktab.homeService.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeService.exceptions.checkes.ManagerNotFoundException;
import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;

import java.util.List;

public interface ManagerService {
    void addSpecialist(SpecialistDto specialistDto);

    void addCustomer(CustomerDto customerDto) throws Exception;

    void deleteSpecialistFromService(String ServiceName, Integer id) throws ServiceNotFoundException, SpecialistNotFoundException;

    void editSpecialistFromService(String ServiceName, String SpecialistUsername);

    void addSpecialistToService();

    void addService();

    void addSubService();

    List<ServiceCategoryDto> getServiceCategory();

    List<SubCategoryDto> getSubCategory();

    ManagerDto login(ManagerDto managerDto) throws ManagerNotFoundException;
    //List<User>
}
