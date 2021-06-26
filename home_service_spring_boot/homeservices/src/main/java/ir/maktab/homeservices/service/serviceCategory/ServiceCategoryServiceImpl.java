package ir.maktab.homeservices.service.serviceCategory;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.Specialist;
import ir.maktab.homeservices.data.repository.serviceCategory.ServiceCategoryRepository;
import ir.maktab.homeservices.data.repository.specifications.SpecificationsClass;
import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.dto.SpecialistDto;
import ir.maktab.homeservices.exceptions.checkes.ServiceAlreadyExistException;
import ir.maktab.homeservices.exceptions.checkes.ServiceDuplicateException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public List<ServiceCategoryDto> showServicesNotInSpecialistServiceList(SpecialistDto specialistDto) {
        return serviceCategoryRepository.findAll(Specification.where(SpecificationsClass.filterProducts(specialistDto)))
                .stream().map(mapper::toServiceCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void addServiceToSpecialist(String serviceName, SpecialistDto specialistDto) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        ServiceCategory service = serviceCategoryRepository.findByName(serviceName);
        SpecialistDto dto = specialistService.findByUsername(specialistDto.getUsername());
        if(dto.getServiceCategoryList().stream().noneMatch(s->s.getName().equals(serviceName))) {
            ServiceCategoryDto serviceCategoryDto = mapper.toServiceCategoryDto(service);
            dto.getServiceCategoryList().add(serviceCategoryDto);
            specialistService.update(dto);
        }
        else
            throw new ServiceAlreadyExistException(maktabMessageSource.getEnglish("service.exist.in.specialist.service.list",new Object[]{serviceName}));
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
