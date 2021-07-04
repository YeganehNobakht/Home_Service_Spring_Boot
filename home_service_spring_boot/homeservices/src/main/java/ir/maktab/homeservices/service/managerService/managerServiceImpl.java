package ir.maktab.homeservices.service.managerService;

import ir.maktab.homeservices.data.entity.Manager;
import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.repository.manager.ManagerRepository;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.exceptions.checkes.ManagerNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeservices.service.customerService.CustomerService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.ManagerMapper;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import ir.maktab.homeservices.service.userService.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final Mapper mapper;
    private final UserService userService;

    public managerServiceImpl(CustomerService customerService, SpecialistService specialistService, ServiceCategoryService serviceCategoryService, ManagerMapper managerMapper, ManagerRepository managerRepository, MaktabMessageSource maktabMessageSource, Mapper mapper, UserService userService) {
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.serviceCategoryService = serviceCategoryService;
        this.managerMapper = managerMapper;
        this.managerRepository = managerRepository;
        this.maktabMessageSource = maktabMessageSource;
        this.mapper = mapper;
        this.userService = userService;
    }


    @Override
    public void addSpecialist(SpecialistDto specialistDto) {

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
            throw new SpecialistNotFoundException(maktabMessageSource.getEnglish("specialist.not.found", new Object[]{id}));
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
        Optional<Manager> manager = managerRepository.findByUsernameAndPassword(managerDto.getUsername(), managerDto.getPassword());
        if (manager.isPresent()) {
            return managerMapper.toManagerDto(manager.get());
        }
        throw new ManagerNotFoundException(maktabMessageSource.getEnglish("manager.not.found", new Object[]{managerDto.getUsername()}));

    }

    @Override
    public List<UserDto> filterUser(UserFilter user) throws ServiceNotFoundException {
        return userService.filterUser(user, getService(user));
    }

    @Override
    public ManagerDto findByUsername(String username) throws ManagerNotFoundException {
        Optional<Manager> manager = managerRepository.findByUsername(username);
        if (manager.isPresent())
            return managerMapper.toManagerDto(manager.get());
        throw new ManagerNotFoundException(maktabMessageSource.getEnglish("manager.not.found", new Object[]{username}));

    }

    @Override
    public void confirmUser(Integer id) throws UserNotFoundException {
        userService.confirmeUser(id);
    }


    private ServiceCategory getService(UserFilter user) throws ServiceNotFoundException {
        ServiceCategoryDto serviceCategoryDto = null;
        if (!StringUtils.isEmpty(user.getSpeciality())) {
            serviceCategoryDto = serviceCategoryService.getByName(user.getSpeciality());
        }
        ServiceCategory service = null;
        if (serviceCategoryDto != null) {
            service = mapper.toServiceCategory(serviceCategoryDto);
            return service;
        }
        return null;
    }
}
