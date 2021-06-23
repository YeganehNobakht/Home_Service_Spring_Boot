package ir.maktab.service.web;

import ir.maktab.service.dto.*;
import ir.maktab.service.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.service.exceptions.checkes.SubServiceNotFoundException;
import ir.maktab.service.service.customerOrderService.CustomerOrderService;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.serviceCategory.ServiceCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yeganeh Nobakht
 **/

@Controller
@RequestMapping("/order")
@SessionAttributes("newOrder")
public class OrderController {
    private final Logger logger = LogManager.getLogger(OrderController.class);
    private final ServiceCategoryService categoryService;
    private final CustomerOrderService customerOrderService;
    private final MaktabMessageSource maktabMessageSource;

    public OrderController(ServiceCategoryService categoryService, CustomerOrderService customerOrderService, MaktabMessageSource maktabMessageSource) {
        this.categoryService = categoryService;
        this.customerOrderService = customerOrderService;
        this.maktabMessageSource = maktabMessageSource;
    }

    @GetMapping("/show")
    public String createOrder(@SessionAttribute("myCustomerDto")CustomerDto customerDto, Model model, HttpServletRequest request){

        logger.info("...add an order...");
        if (customerOrderService.findByCustomer(customerDto).size() == 0) {
            List<ServiceCategoryDto> all = categoryService.getAll();

            model.addAttribute("newOrder", new OrderDto());
            model.addAttribute("serviceList", all);
            model.addAttribute("selectedService", "select");
            HttpSession session = request.getSession();
            session.setAttribute("serviceList", all);
            session.setAttribute("newOrder", model.getAttribute("newOrder"));
            return "showOrder";
        }
        else {
            logger.warn("...unfinished order...");
            model.addAttribute("success", maktabMessageSource.getEnglish("You.already.have.an.active.order"));
            return "customerSuccessPage";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @PostMapping("/add")
    public ModelAndView addOrder(@ModelAttribute("newOrder")
                                     @Validated OrderDto orderDto,
                                 @SessionAttribute("myCustomerDto") CustomerDto customerDto,
                                 HttpServletRequest request) throws ServiceNotFoundException, SubServiceNotFoundException {
        logger.info("...create an order...");
        orderDto.setCustomerDto(customerDto);
        customerOrderService.addOrder(orderDto);
        Map<String, String> successMsg = new HashMap<>();
        successMsg.put("success", maktabMessageSource.getEnglish("order.added"));
        return new ModelAndView("customerSuccessPage", successMsg);
    }


}
