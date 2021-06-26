package ir.maktab.homeservices.service.serviceCategory;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.exceptions.checkes.ServiceAlreadyExistException;
import ir.maktab.homeservices.exceptions.checkes.ServiceDuplicateException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategory get(Integer id) throws ServiceNotFoundException;

    ServiceCategoryDto getByName(String name) throws ServiceNotFoundException;

    void addServiceCategory(ServiceCategoryDto serviceCategoryDto) throws ServiceDuplicateException;

    void updateSpecialist(SpecialistDto specialistDto) ;

    void deleteSpecialist(ServiceCategoryDto serviceCategoryDto, SpecialistDto specialistDto) throws ServiceNotFoundException, SpecialistNotFoundException;

    void addSpecialist(ServiceCategoryDto serviceCategoryDto, SpecialistDto specialistDto) throws ServiceNotFoundException, SpecialistNotFoundException;

    void update(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException;

    List<ServiceCategoryDto> getAll();

    void delete(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException;

    ServiceCategoryDto sava(ServiceCategoryDto serviceCategoryDto);

    List<ServiceCategoryDto> showServicesNotInSpecialistServiceList(SpecialistDto specialistDto);

    void addServiceToSpecialist(String serviceName, SpecialistDto specialistDto) throws SpecialistNotFoundException, ServiceAlreadyExistException;
}
