package ir.maktab.homeservices.web;

import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.exceptions.checkes.ManagerNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.ServiceAlreadyExistException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SpecialistNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.managerService.ManagerService;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import ir.maktab.homeservices.service.userService.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/mngr")
public class managerController {
    private final Logger logger = LogManager.getLogger(managerController.class);
    private final ManagerService managerService;
    private final UserService userService;
    private final ServiceCategoryService serviceCategoryService;
    private final SpecialistService specialistService;
    private final MaktabMessageSource maktabMessageSource;

    public managerController(ManagerService managerService, UserService userService, ServiceCategoryService serviceCategoryService, SpecialistService specialistService, MaktabMessageSource maktabMessageSource) {
        this.managerService = managerService;
        this.userService = userService;
        this.serviceCategoryService = serviceCategoryService;
        this.specialistService = specialistService;
        this.maktabMessageSource = maktabMessageSource;
    }

    @GetMapping("/login")
    public ModelAndView showManagerPage() {
        logger.info("...manager login...");
        return new ModelAndView("managerLogin", "managerDto", new ManagerDto());
    }

    @PostMapping("/register")
    public String managerLogin
            (@ModelAttribute("managerDto") @Valid ManagerDto managerDto,
             BindingResult bindingResult, Model model,
             HttpServletRequest request) throws ManagerNotFoundException {
        logger.info("...manager register...");
        ManagerDto myManagerDto = managerService.login(managerDto);
        HttpSession session = request.getSession(true);
        session.setAttribute("myManagerDto", myManagerDto);
        return "managerService";
    }

    @GetMapping("/userInfo")
    public ModelAndView getUsers(Model model, HttpServletRequest request){
        logger.info("...show user informations...");
        List<UserDto> allUser = userService.findAll();
        model.addAttribute("allUser",allUser);
        List<ServiceCategoryDto> all = serviceCategoryService.getAll();
        HttpSession session = request.getSession();
        session.setAttribute("services",all);
        session.setAttribute("role",new UserRole[]{UserRole.Customer, UserRole.Specialist});
        return new ModelAndView("managerGetUserInfo","userDto",new UserFilter());
    }

    @PostMapping("/search")
    public ModelAndView searchSuggestions(@ModelAttribute("userDto") UserFilter userFilter) throws ServiceNotFoundException {
        logger.info("...filter for user information...");
        return new ModelAndView("managerGetUserInfo","allUser",managerService.filterUser(userFilter));
    }

    @GetMapping("/addService")
    public ModelAndView addService(){
        logger.info("...adding service...");
        return new ModelAndView("managerAddService","serviceDto",new ServiceCategoryDto());
    }

    @GetMapping("/subService")
    public ModelAndView addSubService(Model model){
        logger.info("...adding sub-service...");
        List<ServiceCategoryDto> all = serviceCategoryService.getAll();
        model.addAttribute("serviceList",all);
        return new ModelAndView("managerAddSubService","subServiceDto",new SubCategoryDto());
    }

    @GetMapping("/Speciality")
    public String showAllSpecialistAndTheirSpeciality(Model model){
        List<SpecialistDto> specialistDtoList = specialistService.findAll();

        model.addAttribute("specialistDtoList",specialistDtoList);
        return "managerAddSpeciality";
    }
    @GetMapping("/addSpeciality/{id}")
    public String addSpecialityToSpecialist(@PathVariable("id") Integer id, HttpServletRequest request,
                                            Model model) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        SpecialistDto specialistDto = specialistService.findById(id);
        HttpSession session = request.getSession(true);
        session.setAttribute("specialistForAddSpeciality",specialistDto);
        List<ServiceCategoryDto> serviceList = serviceCategoryService.getAll();
        model.addAttribute("serviceList",serviceList);
        session.setAttribute("serviceList",serviceList);
        return "managerAddingSpeciality";
    }
    @PostMapping("/addingSpecialist")
    public String addingSpeciality(@SessionAttribute("specialistForAddSpeciality")SpecialistDto specialistDto,
                                   @RequestParam("service")String serviceName,
                                   Model model) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        serviceCategoryService.addServiceToSpecialist(serviceName,specialistDto);
        model.addAttribute("success",maktabMessageSource.getEnglish("service.to.specialist.add",new Object[]{serviceName}));
        return "managerSuccessPage";

    }
}
