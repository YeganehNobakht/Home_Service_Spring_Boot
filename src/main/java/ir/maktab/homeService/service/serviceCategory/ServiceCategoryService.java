package ir.maktab.homeService.service.serviceCategory;

import ir.maktab.homeService.data.entity.ServiceCategory;
import ir.maktab.homeService.dto.ServiceCategoryDto;
import ir.maktab.homeService.dto.SpecialistDto;
import ir.maktab.homeService.exceptions.checkes.ServiceDuplicateException;
import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeService.exceptions.checkes.SpecialistNotFoundException;

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
}
