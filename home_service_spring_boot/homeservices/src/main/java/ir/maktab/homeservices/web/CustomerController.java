package ir.maktab.homeservices.web;

import ir.maktab.homeservices.data.entity.Customer;
import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.customerCommentService.CustomerCommentService;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.customerService.CustomerService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.securityService.SecurityService;
import ir.maktab.homeservices.service.siteUrl.SiteUrl;
import ir.maktab.homeservices.service.specialistService.SpecialistService;
import ir.maktab.homeservices.service.suggestionService.SuggestionService;
import ir.maktab.homeservices.service.validation.OnIncreaseBalance;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yeganeh Nobakht
 **/
@Controller
@RequestMapping("/customer")
@SessionAttributes("customerDto")
public class CustomerController {

    private final Logger logger = LogManager.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final SuggestionService suggestionService;
    private final CustomerOrderService customerOrderService;
    private final MaktabMessageSource maktabMessageSource;
    private final SpecialistService specialistService;
    private final CustomerCommentService customerCommentService;
    private final SiteUrl siteUrl;
    private final SecurityService securityService;

    public CustomerController(CustomerService customerService, SuggestionService suggestionService, CustomerOrderService customerOrderService, MaktabMessageSource maktabMessageSource, SpecialistService specialistService, CustomerCommentService customerCommentService, SiteUrl siteUrl, SecurityService securityService) {
        this.customerService = customerService;
        this.suggestionService = suggestionService;
        this.customerOrderService = customerOrderService;
        this.maktabMessageSource = maktabMessageSource;
        this.specialistService = specialistService;
        this.customerCommentService = customerCommentService;
        this.siteUrl = siteUrl;
        this.securityService = securityService;
    }

    /*
     * Add customerDto in model attribute
     */
    @ModelAttribute("customerDto")
    public CustomerDto setUpUserForm() {
        return new CustomerDto();
    }

    @GetMapping(value = {"/login"})
    public ModelAndView showCustomerPage() {
        logger.info("...redirect to customer login page...");
        return new ModelAndView("customerLogin", "customerDto", new CustomerDto());
    }

    @GetMapping("/register")
    public String customerLogin(
            HttpServletRequest request) throws PasswordNotFoundException, UserNameNotFoundException, CustomerNotFoundException {

        logger.info("...redirect to customer register page...");
        HttpSession session = request.getSession(false);
        CustomerDto dto = customerService.findByUsername((String) session.getAttribute("username"));
        session.setAttribute("myCustomerDto", dto);
        return "customerService";
    }

    @GetMapping("/signUp")
    public ModelAndView showCustomerSignUp() {
        return new ModelAndView("customerSignUp", "customerDto", new CustomerDto());
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute("customerDto") @Validated(OnRegister.class) CustomerDto customerDto,
                         HttpServletRequest request, Model model) throws Exception {
        customerService.registerCustomer(customerDto, siteUrl.getSiteURL(request));
        return "register_success";
    }

    @GetMapping("/changePass")
    public ModelAndView changePass() {
        logger.info("...redirect to customer change password page...");
        return new ModelAndView("customerChangePass", "changePass", new ChangePassDto());
    }

    @PostMapping("/change")
    public ModelAndView change(@ModelAttribute("changePass") @Valid ChangePassDto changePassDto,
                               @SessionAttribute("myCustomerDto") CustomerDto customerDto) throws CustomerNotFoundException, PasswordNotFoundException {

        logger.info("...changing customer password process...");
        Customer customer = customerService.changePassword(customerDto.getUsername(), changePassDto.getOldPass(), changePassDto.getNewPass());
        Map<String, String> message = new HashMap<>();
        message.put("message", maktabMessageSource.getEnglish("Password.successfully.changed"));
        return new ModelAndView("customerChangePass", message);
    }

    @GetMapping("showOrders")
    public String ShowOrders() throws Exception {

        logger.info("...redirect to customer show orders page...");
        return "customerShowOrders";
    }

    @GetMapping("/currentOrder")
    public String currentOrder(@SessionAttribute("myCustomerDto") CustomerDto customerDto, Model model) {

        logger.info("...redirect to customer current order page...");
        List<CustomerOrderDto> orders = customerOrderService.findByOrderStatusNotAndCustomer_Id(OrderStatus.PAID, customerDto.getId());
        if (orders.size() == 0) {
            model.addAttribute("message", maktabMessageSource.getEnglish("No.current.order"));
        }
        model.addAttribute("orders", orders);
        return "customerCurrentOrder";
    }

    @GetMapping("/getSuggestion/{orderId}")
    public String findOrder(@PathVariable(value = "orderId") Integer orderId,
                            Model model, HttpServletRequest request) throws OrderException, OrderNoSuggestionException, OrderNotFoundException {

        logger.info("...show all suggestions that related to an order...");
        CustomerOrderDto orderDto = customerOrderService.findById(orderId);
        if (orderDto.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_OFFER)) {
            logger.info("...find all order with WAITING_FOR_SPECIALIST_OFFER status...");
            List<SuggestionDto> suggestions = suggestionService.findByCustomerOrder(orderDto);
            model.addAttribute("suggestions", suggestions);

            HttpSession session = request.getSession(true);
            session.setAttribute("orderDto", orderDto);

            model.addAttribute("suggestion", new SuggestionDto());

            //TODO:: return error: "id is wrong" to specialistShowOrder
            return "customerOrderSuggestion";
        }
        if (orderDto.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_CHOSE)) {
            logger.info("...order with WAITING_FOR_SPECIALIST_CHOSE status...");
            model.addAttribute("message", maktabMessageSource.getEnglish("Your.order.is.waiting.for.expert.approval"));
            return "customerCurrentOrder";
        }
        if (orderDto.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_COME)) {
            logger.info("...order with WAITING_FOR_SPECIALIST_COME status...");
            model.addAttribute("message", maktabMessageSource.getEnglish("Your.order.is.waiting.for.a.specialist.to.arrive"));
            return "customerCurrentOrder";
        }
        if (orderDto.getOrderStatus().equals(OrderStatus.STARTED)) {
            logger.info("... order with STARTED status...");
            model.addAttribute("message", maktabMessageSource.getEnglish("Your.order.is.in.progress"));
            return "customerCurrentOrder";
        }
        if (orderDto.getOrderStatus().equals(OrderStatus.FINISHED_WORK)) {
            logger.info("...order with FINISHED_WORK status...");
            model.addAttribute("message", maktabMessageSource.getEnglish("Your.order.is.finished,.now.you.can.pay.it"));
            return "customerCurrentOrder";
        }
        if (orderDto.getOrderStatus().equals(OrderStatus.WAIT_FOR_PAID)) {
            logger.info("...order with WAIT_FOR_PAID status...");
            HttpSession session = request.getSession(true);
            session.setAttribute("price", orderDto.getPrice());
            session.setAttribute("customerOrderDto", orderDto);
            return "customerPaymentInformations";
        }
        return "customerCurrentOrder";
    }

    @GetMapping("/selectASuggestion/{suggestionId}")
    public String getSuggestion(@PathVariable(value = "suggestionId") Integer id,
                                Model model, HttpServletRequest request,
                                @SessionAttribute("orderDto") CustomerOrderDto orderDto)
            throws SuggestionNotFoundException, OrderException,
            OrderNoSuggestionException,
            OrderNotFoundException {

        logger.info("...set a suggestion for an order...");
        suggestionService.selectASuggestion(id, orderDto);
        model.addAttribute("success", "Suggestion successfully registered.");
        return "customerSuccessPage";

    }

    @GetMapping("/finishOrder")
    public String finishOrder(@SessionAttribute("myCustomerDto") CustomerDto customerDto,
                              Model model) {
        logger.info("...show finishing orders...");
        List<CustomerOrderDto> customerOrderDto = new ArrayList<>();
        try {
            customerOrderDto = customerOrderService.findUserByStatusAndCustomer(OrderStatus.WAIT_FOR_PAID, customerDto);
        } catch (OrderNotFoundException e) {
            logger.warn("...there is not any finished order...");
            model.addAttribute("message", e.getMessage()/*maktabMessageSource.getEnglish(e.getMessage(),new Object[]{OrderStatus.WAIT_FOR_PAID,customerDto.getUsername()})*/);
            model.addAttribute("order", customerOrderDto);
            return "customerOrderForPay";
        }
        model.addAttribute("order", customerOrderDto);
        return "customerOrderForPay";
    }

    @GetMapping("/paymentInformations/{orderId}")
    public String getPaymentInformations(@PathVariable(value = "orderId") Integer id,
                                         Model model, HttpServletRequest request) throws OrderNotFoundException {
        logger.info("...show payment information for customer...");
        CustomerOrderDto customerOrderDto = customerOrderService.findById(id);
        model.addAttribute("customerOrderDto", customerOrderDto);
        HttpSession session = request.getSession(true);
        session.setAttribute("price", customerOrderDto.getPrice());
        session.setAttribute("customerOrderDto", customerOrderDto);
        return "customerPaymentInformations";
    }

    @GetMapping("/completeOrder")
    public String showCompleteOrder(@SessionAttribute("myCustomerDto") CustomerDto customerDto, Model model) throws OrderNotFoundException {
        logger.info("...show completed order...");
        List<CustomerOrderDto> orderDto = customerOrderService.findUserByStatusAndCustomer(OrderStatus.PAID, customerDto);
        model.addAttribute("orderDto", orderDto);
        return "customerCompletedOrder";
    }

    @GetMapping("/increaseBalance")
    public ModelAndView increaseBalance() {
        return new ModelAndView("customerIncreaseBalance", "customerDto", new CustomerDto());
    }

    @PostMapping("/increaseBalance")
    public ModelAndView increase(@ModelAttribute("customerDto") @Validated(OnIncreaseBalance.class) CustomerDto customerDto,
                                 HttpServletRequest request) {
        logger.info("Increase customer balance with username: " + customerDto.getUsername());
        request.getSession().setAttribute("balance", customerDto.getBalance());
        return new ModelAndView("increaseBalance", "paymentDto", new PaymentDto());
    }

    @GetMapping("/commentPage/{orderId}")
    public ModelAndView showCommentPage(@PathVariable(value = "orderId") Integer id,
                                        HttpServletRequest request) {
        request.getSession(true).setAttribute("commentOrderId", id);
        return new ModelAndView("customerComment", "customerCommentDto", new CustomerCommentDto());
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute("customerCommentDto") @Valid CustomerCommentDto customerCommentDto,
                             @SessionAttribute("commentOrderId") Integer orderId,
                             @SessionAttribute("myCustomerDto") CustomerDto customerDto, Model model) throws OrderNotFoundException {
        logger.info("...adding comment to order with id :" + orderId);
        CustomerOrderDto orderDto = customerOrderService.findById(orderId);
        customerCommentDto.setCustomerDto(customerDto).setSpecialistDto(orderDto.getSpecialistDto());
        orderDto.setCustomerCommentDto(customerCommentDto);
        CustomerCommentDto commentDto = customerCommentService.save(customerCommentDto);
        customerOrderService.updateComment(orderId, commentDto);
        specialistService.updateRate(orderDto.getSpecialistDto(), Double.parseDouble(commentDto.getScore()));
        model.addAttribute("success", maktabMessageSource.getEnglish("comment.add", new Object[]{orderId}));
        return "customerSuccessPage";
    }
}
