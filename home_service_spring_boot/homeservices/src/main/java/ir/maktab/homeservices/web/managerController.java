package ir.maktab.homeservices.web;

import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.managerService.ManagerService;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import ir.maktab.homeservices.service.subCategoryService.SubCategoryService;
import ir.maktab.homeservices.service.userService.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final SubCategoryService subCategoryService;

    public managerController(ManagerService managerService, UserService userService, ServiceCategoryService serviceCategoryService, SpecialistService specialistService, MaktabMessageSource maktabMessageSource, SubCategoryService subCategoryService) {
        this.managerService = managerService;
        this.userService = userService;
        this.serviceCategoryService = serviceCategoryService;
        this.specialistService = specialistService;
        this.maktabMessageSource = maktabMessageSource;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/login")
    public ModelAndView showManagerPage() {
        logger.info("...manager login...");
        return new ModelAndView("managerLogin", "managerDto", new ManagerDto());
    }

    @GetMapping("/register")
    public String managerLogin(HttpServletRequest request) throws ManagerNotFoundException, SpecialistNotFoundException {
        logger.info("...manager register...");
        HttpSession session = request.getSession(false);
        ManagerDto dto = managerService.findByUsername((String) session.getAttribute("loginUsername"));

        session.setAttribute("myManagerDto", dto);
        return "managerService";
    }

    @GetMapping("/userInfo")
    public ModelAndView getUsers(Model model, HttpServletRequest request) throws UserNotFoundException {
        logger.info("...show user informations...");
        List<UserDto> allUser = userService.findAll();
        model.addAttribute("allUser", allUser);
        List<ServiceCategoryDto> all = serviceCategoryService.getAll();
        HttpSession session = request.getSession();
        session.setAttribute("services", all);
        session.setAttribute("role", new UserRole[]{UserRole.Customer, UserRole.Specialist});
        session.setAttribute("userStatus", UserStatus.WAITING);
        return new ModelAndView("managerGetUserInfo", "userDto", new UserFilter());
    }

    @PostMapping("/search")
    public ModelAndView searchSuggestions(@ModelAttribute("userDto") UserFilter userFilter) throws ServiceNotFoundException {
        logger.info("...filter for user information...");
        return new ModelAndView("managerGetUserInfo", "allUser", managerService.filterUser(userFilter));
    }

    @GetMapping("/addService")
    public ModelAndView addService() {
        logger.info("...adding service...");
        return new ModelAndView("managerAddService", "serviceDto", new ServiceCategoryDto());
    }

    @GetMapping("/subService")
    public ModelAndView addSubService(HttpServletRequest request, Model model) {
        logger.info("...adding sub-service...");
        List<ServiceCategoryDto> all = serviceCategoryService.getAll();
        request.getSession().setAttribute("serviceList", all);
        model.addAttribute("serviceList", all);
        return new ModelAndView("managerAddSubService", "subServiceDto", new SubCategoryDto());
    }

    @GetMapping("/Speciality")
    public String showAllSpecialistAndTheirSpeciality(Model model) {
        List<SpecialistDto> specialistDtoList = specialistService.findApproveSpecialist();
        model.addAttribute("specialistDtoList", specialistDtoList);
        return "managerAddSpeciality";
    }

    @GetMapping("/addSpeciality/{id}")
    public String addSpecialityToSpecialist(@PathVariable("id") Integer id, HttpServletRequest request,
                                            Model model) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        SpecialistDto specialistDto = specialistService.findById(id);
        HttpSession session = request.getSession(true);
        session.setAttribute("specialistForAddSpeciality", specialistDto);
        List<ServiceCategoryDto> serviceList = serviceCategoryService.getAll();
        model.addAttribute("serviceList", serviceList);
        session.setAttribute("serviceList", serviceList);
        return "managerAddingSpeciality";
    }

    @PostMapping("/addingSpecialist")
    public String addingSpeciality(@SessionAttribute("specialistForAddSpeciality") SpecialistDto specialistDto,
                                   @RequestParam("service") String serviceName,
                                   Model model) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        serviceCategoryService.addServiceToSpecialist(serviceName, specialistDto);
        model.addAttribute("success", maktabMessageSource.getEnglish("service.to.specialist.add", new Object[]{serviceName}));
        return "managerSuccessPage";

    }

    @GetMapping("/orderForAUser/{id}")
    public String showOrderForAUser(@PathVariable("id") Integer userId, HttpServletRequest request) {
        request.getSession().setAttribute("orderForUserId", userId);
        request.getSession().setAttribute("orderStatus", OrderStatus.values());
        return "managerGetOrdersForAUser";
    }

    @GetMapping("/reportOfUsers")
    public String reportingUsers() {
        return "managerReportingUsers";
    }

    @GetMapping("/reportOfOrders")
    public String reportingOrders(Model model) {
        List<ServiceCategoryDto> all = serviceCategoryService.getAll();
        List<SubCategoryDto> SubServiceList = subCategoryService.getAll();
        model.addAttribute("serviceList", all);
        model.addAttribute("SubServiceList", SubServiceList);
        model.addAttribute("status", OrderStatus.values());
        return "managerReportingOrders";
    }

    @GetMapping("/confirmUser/{id}")
    public String confirmUser(@PathVariable("id") Integer id, HttpServletRequest request, Model model) throws UserNotFoundException {
        managerService.confirmUser(id);
        List<UserDto> allUser = userService.findAll();
        model.addAttribute("allUser", allUser);
        model.addAttribute("userDto", new UserFilter());
        model.addAttribute("message", maktabMessageSource.getEnglish("user.status.update", new Object[]{id}));
        return "managerGetUserInfo";
    }

}
