package ir.maktab.service.service.serviceCategory;

import ir.maktab.service.data.entity.ServiceCategory;
import ir.maktab.service.data.entity.Specialist;
import ir.maktab.service.data.repository.serviceCategory.ServiceCategoryRepository;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SpecialistDto;
import ir.maktab.service.exceptions.checkes.ServiceDuplicateException;
import ir.maktab.service.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.service.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.mapper.Mapper;
import ir.maktab.service.service.specialistService.SpecialistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;
    private final Mapper mapper;
    private final SpecialistService specialistService;
    private final MaktabMessageSource maktabMessageSource;

    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository, Mapper mapper, SpecialistService specialistService, MaktabMessageSource maktabMessageSource) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.mapper = mapper;
        this.specialistService = specialistService;
        this.maktabMessageSource = maktabMessageSource;
    }


    @Override
    public ServiceCategory get(Integer id) throws ServiceNotFoundException {
        Optional<ServiceCategory> serviceCategory = serviceCategoryRepository.findById(id);
        if (serviceCategory.isPresent())
            return serviceCategory.get();
        else
            throw new ServiceNotFoundException(maktabMessageSource.getEnglish("service.not.found"));
    }

    @Override
    public ServiceCategoryDto getByName(String name) throws ServiceNotFoundException {
        ServiceCategory serviceCategory = serviceCategoryRepository.findByName(name);
        if (serviceCategory != null)
            return mapper.toServiceCategoryDto(serviceCategory);
        else
            throw new ServiceNotFoundException(maktabMessageSource.getEnglish("service.not.found"));
    }

    @Override
    public void addServiceCategory(ServiceCategoryDto serviceCategoryDto) throws ServiceDuplicateException {
        if (serviceCategoryRepository.findByName(serviceCategoryDto.getName()) != null) {
            throw new ServiceDuplicateException(maktabMessageSource.getEnglish("service.duplicate"));
        } else {
            serviceCategoryRepository.save(mapper.toServiceCategory(serviceCategoryDto));
        }
    }

    @Override
    public void updateSpecialist(SpecialistDto specialistDto) {
//        specialistService.update(specialistDto);
    }

    @Override
    public void deleteSpecialist(ServiceCategoryDto serviceCategoryDto, SpecialistDto specialistDto) throws ServiceNotFoundException, SpecialistNotFoundException {
        Specialist specialist = mapper.toSpecialist(specialistDto);
        ServiceCategoryDto serviceCategory = getByName(serviceCategoryDto.getName());

        serviceCategory.getSpecialistList().remove(specialist);
        //using save method for update
        serviceCategoryRepository.save(mapper.toServiceCategory(serviceCategory));

        specialist.getServiceCategoryList().remove(serviceCategory);
        specialistService.update(mapper.toSpecialistDto(specialist));
    }

    @Override
    public void addSpecialist(ServiceCategoryDto serviceCategoryDto, SpecialistDto specialistDto) throws ServiceNotFoundException, SpecialistNotFoundException {

        Specialist specialist = mapper.toSpecialist(specialistDto);
        ServiceCategoryDto serviceCategory = getByName(serviceCategoryDto.getName());

        serviceCategoryDto.getSpecialistList().add(mapper.toSpecialistDto(specialist));
        //using save method for update
        serviceCategoryRepository.save(mapper.toServiceCategory(serviceCategory));

        specialist.getServiceCategoryList().add(mapper.toServiceCategory(serviceCategory));
        specialistService.update(mapper.toSpecialistDto(specialist));
    }

    @Override
    public void update(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException {
        Optional<ServiceCategory> serviceCategoryRepositoryById = serviceCategoryRepository.findById(serviceCategoryDto.getId());
        if (serviceCategoryRepositoryById.isPresent()) {
            serviceCategoryRepository.save(serviceCategoryRepositoryById.get());
        } else
            throw new ServiceNotFoundException(maktabMessageSource.getEnglish("service.not.found"));
    }

    @Override
    public List<ServiceCategoryDto> getAll() {
        List<ServiceCategory> subCategoryList = serviceCategoryRepository.findAll();
        return subCategoryList.stream().map(mapper::toServiceCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void delete(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException {
        Optional<ServiceCategory> serviceCategory = serviceCategoryRepository.findById(serviceCategoryDto.getId());
        if (serviceCategory.isPresent()) {
            serviceCategoryRepository.delete(serviceCategory.get());
        } else
            throw new ServiceNotFoundException(maktabMessageSource.getEnglish("service.not.found"));

    }

    @Override
    public ServiceCategoryDto sava(ServiceCategoryDto serviceCategoryDto) {
        ServiceCategory serviceCategory = serviceCategoryRepository.save(mapper.toServiceCategory(serviceCategoryDto));
        return mapper.toServiceCategoryDto(serviceCategory);
    }

//    @Override
//    public void updateSpecialist(ServiceCategoryDto serviceCategoryDto, SpecialistDto specialistDto) throws Exception {
////        ServiceCategory serviceCategory = serviceCategoryMapper.toServiceCategory(serviceCategoryDto);
////        Specialist specialist = specialistMapper.toSpecialist(specialistDto);
////        if (serviceCategory.getSpecialistList().stream().anyMatch(specialist1 -> specialist1.getUsername().equals(specialist.getUsername()))){
////
////        }
//        if (specialistService.get(specialistDto.getUsername())!=null){
//            specialistService.update(specialistDto);
//        }
//        specialistService.update(specialistDto);
//
//    }


}
