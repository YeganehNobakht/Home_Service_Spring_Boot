package ir.maktab.homeService.service.managerService;


import ir.maktab.homeService.dto.*;
import ir.maktab.homeService.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeService.service.mapper.ManagerMapper;
import ir.maktab.homeService.service.specialistService.SpecialistService;
import ir.maktab.homeService.data.entity.Manager;
import ir.maktab.homeService.data.repository.manager.ManagerRepository;
import ir.maktab.homeService.exceptions.checkes.ManagerNotFoundException;
import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeService.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeService.service.customerService.CustomerService;
import ir.maktab.homeService.service.serviceCategory.ServiceCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class managerServiceImpl implements ManagerService {

    private final CustomerService customerService;
    private final SpecialistService specialistService;
    private final ServiceCategoryService serviceCategoryService;
    private final ManagerMapper managerMapper;
    private final ManagerRepository managerRepository;
    private final MaktabMessageSource maktabMessageSource;

    public managerServiceImpl(CustomerService customerService, SpecialistService specialistService, ServiceCategoryService serviceCategoryService, ManagerMapper managerMapper, ManagerRepository managerRepository, MaktabMessageSource maktabMessageSource) {
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.serviceCategoryService = serviceCategoryService;
        this.managerMapper = managerMapper;
        this.managerRepository = managerRepository;
        this.maktabMessageSource = maktabMessageSource;
    }


    @Override
    public void addSpecialist(SpecialistDto specialistDto)  {

    }

    @Override
    public void addCustomer(CustomerDto customerDto) throws Exception {

    }

    @Override
    public void deleteSpecialistFromService(String serviceName, Integer id) throws ServiceNotFoundException, SpecialistNotFoundException {
        ServiceCategoryDto service = serviceCategoryService.getByName(serviceName);
        if (service.getSpecialistList().stream().anyMatch(specialist -> specialist.getId().equals(id)))
            specialistService.delete(id);
        else
            throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.not.found",new Object[]{id}));
    }

    @Override
    public void editSpecialistFromService(String ServiceName, String SpecialistUsername) {

    }

    @Override
    public void addSpecialistToService() {

    }

    @Override
    public void addService() {

    }

    @Override
    public void addSubService() {

    }

    @Override
    public List<ServiceCategoryDto> getServiceCategory() {
        return null;
    }

    @Override
    public List<SubCategoryDto> getSubCategory() {
        return null;
    }

    @Override
    public ManagerDto login(ManagerDto managerDto) throws ManagerNotFoundException {
        Optional<Manager> manager = managerRepository.findByUsernameAndPassword(managerDto.getUsername(),managerDto.getPassword());
        if (manager.isPresent()) {
           return managerMapper.toManagerDto(manager.get());
        }
        throw new ManagerNotFoundException(maktabMessageSource.getEnglish("manager.not.found",new Object[]{managerDto.getUsername()}));

    }
}
