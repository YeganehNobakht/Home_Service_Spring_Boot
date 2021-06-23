package ir.maktab.service.service.subCategoryService;

import ir.maktab.service.data.entity.SubCategory;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SubCategoryDto;
import ir.maktab.service.exceptions.checkes.SubServiceNotFoundException;

import java.util.List;

public interface SubCategoryService {
    SubCategory get(Integer id) throws SubServiceNotFoundException;

    SubCategoryDto getByName(String name) throws SubServiceNotFoundException;

    void addSubService(ServiceCategoryDto serviceCategoryDto, SubCategoryDto subCategoryDto) throws Exception;

    void update(SubCategoryDto subCategoryDto) throws SubServiceNotFoundException;

    List<SubCategoryDto> getAll();

    void delete(SubCategoryDto subCategoryDto) throws SubServiceNotFoundException;

    List<String> getByServiceName(String serviceName);

    SubCategoryDto sava(SubCategoryDto subCategoryDto);
}
