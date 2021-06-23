package ir.maktab.service.service.serviceCategory;

import ir.maktab.service.data.entity.ServiceCategory;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SpecialistDto;
import ir.maktab.service.exceptions.checkes.ServiceDuplicateException;
import ir.maktab.service.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.service.exceptions.checkes.SpecialistNotFoundException;

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
