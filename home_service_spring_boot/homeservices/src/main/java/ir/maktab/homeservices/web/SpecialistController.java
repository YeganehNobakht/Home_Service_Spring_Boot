package ir.maktab.homeservices.web;

import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import ir.maktab.homeservices.data.entity.enums.SuggestionStatus;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.siteUrl.SiteUrl;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import ir.maktab.homeservices.service.suggestionService.SuggestionService;
import ir.maktab.homeservices.service.validation.OnRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yeganeh Nobakht
 **/
@Controller
@RequestMapping("/specialist")
public class SpecialistController {
    private final Logger logger = LogManager.getLogger(SpecialistController.class);
    private final SpecialistService specialistService;
    private final CustomerOrderService customerOrderService;
    private final ServiceCategoryService serviceCategoryService;
    private final SuggestionService suggestionService;
    private final MaktabMessageSource maktabMessageSource;
    private final SiteUrl siteUrl;

    public SpecialistController(SpecialistService specialistService, CustomerOrderService customerOrderService, ServiceCategoryService serviceCategoryService, SuggestionService suggestionService, MaktabMessageSource maktabMessageSource, SiteUrl siteUrl) {
        this.specialistService = specialistService;
        this.customerOrderService = customerOrderService;
        this.serviceCategoryService = serviceCategoryService;
        this.suggestionService = suggestionService;
        this.maktabMessageSource = maktabMessageSource;
        this.siteUrl = siteUrl;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        logger.info("...specialist login page...");
        return new ModelAndView("specialistLogin", "specialistDto", new SpecialistDto());
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model) throws SpecialistNotFoundException, UserNotApproveException {
        logger.info("...specialist registered...");
        HttpSession session = request.getSession(false);
        SpecialistDto dto = specialistService.findByUsername((String) session.getAttribute("loginUsername"));
        boolean approve = specialistService.checkforStatus(dto);

//        if (approve) {
            session.setAttribute("mySpecialistDto", dto);
            return "specialistService";
   /*     } else {
            return "waitForApprove";
//        }*/
    }

    @GetMapping("/signUp")
    public ModelAndView signUp(HttpServletRequest request) {
        logger.info("...specialist sign up page...");
        HttpSession session = request.getSession(true);
        List<ServiceCategoryDto> allServices = serviceCategoryService.getAll();
        session.setAttribute("allServices", allServices);
        return new ModelAndView("specialistSignUp", "specialistDto", new SpecialistSignUpDto());
    }

    @PostMapping("/registerSignUp")
    public String registerSignUp(@ModelAttribute("specialistDto") @Validated(OnRegister.class) SpecialistSignUpDto specialistDto,
                                 HttpServletRequest request) throws Exception {
        logger.info("...specialist register from sign up page...");
        ServiceCategoryDto service = serviceCategoryService.getByName(specialistDto.getServiceCategory().getName());
        specialistDto.setServiceCategory(service);
        SpecialistDto specialistDto1 = specialistService.registerSpecialist(specialistDto, siteUrl.getSiteURL(request));
        return "register_success";
    }

    @GetMapping("/showOrder")
    public ModelAndView showOrder(@SessionAttribute("mySpecialistDto") SpecialistDto specialistDto) throws ServiceNotFoundException {
        logger.info("...show all unselected order for specialist...");
        Map<String, Object> orderMap = new HashMap<>();
        Set<CustomerOrderDto> orderDtoLists = suggestionService.showOrderWaitForOffer(specialistDto);
        orderMap.put("order", orderDtoLists);
        return new ModelAndView("specialistShowOrder", orderMap);

    }

    @GetMapping("/changePass")
    public ModelAndView changePass() {
        return new ModelAndView("specialistChangePass", "changePass", new ChangePassDto());
    }

    @PostMapping("/change")
    public ModelAndView change(@ModelAttribute("changePass") ChangePassDto changePassDto,
                               @SessionAttribute("mySpecialistDto") SpecialistDto specialistDto) throws SpecialistNotFoundException, PasswordNotFoundException {
        logger.info("...specialist change password...");
        specialistService.changePassword(specialistDto, changePassDto.getOldPass(), changePassDto.getNewPass());
        Map<String, String> message = new HashMap<>();
        message.put("message", maktabMessageSource.getEnglish("pass.change"));
        return new ModelAndView("specialistChangePass", message);
    }

    @GetMapping("/showAllOrders")
    public String showAllOrders() {
        logger.info("...current and completed orders...");
        return "specialistShowAllOrders";
    }

    @GetMapping("/currentOrder")
    public String currentOrder(@SessionAttribute("mySpecialistDto") SpecialistDto specialistDto,
                               HttpServletRequest request, Model model) {
        logger.info("...current orders...");

        List<SuggestionDto> suggestionDtoList = suggestionService.findUserBySuggestionStatusAndSpecialist(SuggestionStatus.ACCEPTED, specialistDto.getId());
        HttpSession session = request.getSession(true);
        session.setAttribute("suggestionList", suggestionDtoList);

        List<CustomerOrderDto> orderDtoList = suggestionDtoList.stream().map(SuggestionDto::getCustomerOrder).collect(Collectors.toList());
        model.addAttribute("orders", orderDtoList);
        return "specialistCurrentOrder";
    }

    @GetMapping("/changeStatus/{orderId}")
    public String findOrder(@PathVariable(value = "orderId") Integer orderId,
                            @SessionAttribute("suggestionList") List<SuggestionDto> suggestionDtoList,
                            Model model, HttpServletRequest request) throws Exception {
        logger.info("...change status of an order which selected...");
        List<SuggestionDto> suggestionDto = suggestionDtoList.stream().filter(s -> s.getCustomerOrder().getId().equals(orderId)).collect(Collectors.toList());
        OrderStatus orderStatus = suggestionDto.get(0).getCustomerOrder().getOrderStatus();
//        if (suggestionDto.get(0).getCustomerOrder().getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_CHOSE)) {
//            customerOrderService.updateOrderStatus(suggestionDto.get(0).getCustomerOrder().setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_COME));
//            model.addAttribute("success", maktabMessageSource.getEnglish("order.accept"));
//            return "specialistSuccessPage";
//        }
        if (suggestionDto.get(0).getCustomerOrder().getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_COME)) {
            customerOrderService.updateOrderStatus(suggestionDto.get(0).getCustomerOrder().setOrderStatus(OrderStatus.STARTED));
            model.addAttribute("success", maktabMessageSource.getEnglish("change.status.to.started"));
            return "specialistSuccessPage";
        }
        if (suggestionDto.get(0).getCustomerOrder().getOrderStatus().equals(OrderStatus.STARTED)) {
            customerOrderService.updateOrderStatus(suggestionDto.get(0).getCustomerOrder().setOrderStatus(OrderStatus.FINISHED_WORK));
            model.addAttribute("success", maktabMessageSource.getEnglish("change.status.to.finished"));
            return "specialistSuccessPage";
        }
        if (suggestionDto.get(0).getCustomerOrder().getOrderStatus().equals(OrderStatus.FINISHED_WORK)) {
            customerOrderService.updateOrderStatus(suggestionDto.get(0).getCustomerOrder().setOrderStatus(OrderStatus.WAIT_FOR_PAY));
            model.addAttribute("success", maktabMessageSource.getEnglish("wait.for.pay"));
            return "specialistSuccessPage";
        }
        if (suggestionDto.get(0).getCustomerOrder().getOrderStatus().equals(OrderStatus.WAIT_FOR_PAY)) {
            model.addAttribute("success", maktabMessageSource.getEnglish("wait.for.pay"));
            return "specialistSuccessPage";
        }
        return "specialistCurrentOrder";
    }

    @GetMapping("/completeOrder")
    public String showCompleteOrder(@SessionAttribute("mySpecialistDto") SpecialistDto specialistDto, Model model) {
        logger.info("...chane status of completed orders...");
        List<SuggestionDto> suggestionDtos = suggestionService.findUserBySuggestionStatusAndSpecialist(SuggestionStatus.DONE, specialistDto.getId());
        List<CustomerOrderDto> orderDto = suggestionDtos.stream().map(SuggestionDto::getCustomerOrder).collect(Collectors.toList());
        model.addAttribute("orderDto", orderDto);
        return "specialistCompletedOrder";
    }

    @GetMapping("/showSpeciality")
    public String showSpeciality(@SessionAttribute("mySpecialistDto") SpecialistDto specialistDto,
                                 HttpServletRequest request, Model model) {
//        List<ServiceCategoryDto> serviceCategoryDtoList = serviceCategoryService.showServicesNotInSpecialistServiceList(specialistDto);
        List<ServiceCategoryDto> serviceCategoryDtoList = serviceCategoryService.getAll();
        HttpSession session = request.getSession(true);
        session.setAttribute("serviceList", serviceCategoryDtoList);
        session.setAttribute("specialistServiceList", specialistDto.getServiceCategoryList());
        model.addAttribute("serviceList", serviceCategoryDtoList);
        model.addAttribute("specialistServiceList", specialistDto.getServiceCategoryList());
        return "specialistShowSpeciality";
    }

    @PostMapping("/addSpeciality")
    public String addSpeciality(@RequestParam("service") String serviceName,
                                @SessionAttribute("mySpecialistDto") SpecialistDto specialistDto, Model model) throws SpecialistNotFoundException, ServiceAlreadyExistException {
        serviceCategoryService.addServiceToSpecialist(serviceName, specialistDto);
        model.addAttribute("success", maktabMessageSource.getEnglish("service.to.specialist.add", new Object[]{serviceName}));
        return "specialistSuccessPage";
    }
}
