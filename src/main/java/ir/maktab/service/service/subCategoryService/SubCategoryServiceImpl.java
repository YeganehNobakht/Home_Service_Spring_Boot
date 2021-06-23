package ir.maktab.service.service.subCategoryService;

import ir.maktab.service.data.entity.SubCategory;
import ir.maktab.service.data.repository.subCategory.SubCategoryRepository;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SubCategoryDto;
import ir.maktab.service.exceptions.checkes.SubServiceNotFoundException;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.mapper.Mapper;
import ir.maktab.service.service.serviceCategory.ServiceCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final ServiceCategoryService serviceCategoryService;
    private final Mapper mapper;
    private final MaktabMessageSource maktabMessageSource;


    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, ServiceCategoryService serviceCategoryService, Mapper mapper, MaktabMessageSource maktabMessageSource) {
        this.subCategoryRepository = subCategoryRepository;
        this.serviceCategoryService = serviceCategoryService;
        this.mapper = mapper;
        this.maktabMessageSource = maktabMessageSource;
    }

    @Override
    public SubCategory get(Integer id) throws SubServiceNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (subCategory.isPresent())
            return subCategory.get();
        else
            throw new SubServiceNotFoundException(maktabMessageSource.getEnglish("sub.service.not.found",new Object[]{id}));
    }

    @Override
    @Transactional
    public SubCategoryDto getByName(String name) throws SubServiceNotFoundException {
        SubCategory subCategory = subCategoryRepository.findByName(name);
        if (subCategory != null)
            return mapper.toSubCategoryDto(subCategory);
        else
            throw new SubServiceNotFoundException(maktabMessageSource.getEnglish("sub.service.not.found",new Object[]{name}));
    }

    @Override
    public void addSubService(ServiceCategoryDto serviceCategoryDto, SubCategoryDto subCategoryDto) throws Exception {
        ServiceCategoryDto serviceByName = serviceCategoryService.getByName(serviceCategoryDto.getName());
        subCategoryDto.setServiceCategory(serviceByName);
        //using save method for update
        subCategoryRepository.save(mapper.toSubCategory(subCategoryDto));
    }

    @Override
    public void update(SubCategoryDto subCategoryDto) throws SubServiceNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryDto.getId());
        if (subCategory.isPresent()) {
            subCategoryRepository.save(subCategory.get());
        } else
            throw new SubServiceNotFoundException(maktabMessageSource.getEnglish("sub.service.not.found",new Object[]{subCategoryDto}));
    }

    @Override
    public List<SubCategoryDto> getAll() {
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        return subCategoryList.stream().map(mapper::toSubCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void delete(SubCategoryDto subCategoryDto) throws SubServiceNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryDto.getId());
        if (subCategory.isPresent()) {
            subCategoryRepository.delete(subCategory.get());
        } else
            throw new SubServiceNotFoundException(maktabMessageSource.getEnglish("sub.service.not.found",new Object[]{subCategoryDto}));
    }

    @Override
    public List<String> getByServiceName(String serviceName) {
        return subCategoryRepository.findByServiceCategoryName(serviceName).stream().map(SubCategory::getName).collect(Collectors.toList());
    }

    @Override
    public SubCategoryDto sava(SubCategoryDto subCategoryDto) {
        SubCategory subCategory = subCategoryRepository.save(mapper.toSubCategory(subCategoryDto));
        return mapper.toSubCategoryDto(subCategory);
    }
}
