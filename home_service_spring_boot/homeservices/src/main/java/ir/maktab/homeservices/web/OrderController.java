package ir.maktab.homeservices.web;

import ir.maktab.homeservices.dto.AddressFromMapDto;
import ir.maktab.homeservices.dto.CustomerDto;
import ir.maktab.homeservices.dto.OrderDto;
import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SubServiceNotFoundException;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
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
    private final RestTemplate restTemplate;

    public OrderController(ServiceCategoryService categoryService, CustomerOrderService customerOrderService, MaktabMessageSource maktabMessageSource, RestTemplate restTemplate) {
        this.categoryService = categoryService;
        this.customerOrderService = customerOrderService;
        this.maktabMessageSource = maktabMessageSource;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/show")
    public String createOrder(@SessionAttribute("myCustomerDto") CustomerDto customerDto, Model model, HttpServletRequest request) {

        logger.info("...add an order...");
//        if (customerOrderService.findByCustomerAndOrderStatusNotPaid(customerDto).size() == 0) {
        List<ServiceCategoryDto> all = categoryService.getAll();

        model.addAttribute("newOrder", new OrderDto());
        model.addAttribute("serviceList", all);
        model.addAttribute("selectedService", "select");
        HttpSession session = request.getSession();
        session.setAttribute("serviceList", all);
        session.setAttribute("newOrder", model.getAttribute("newOrder"));
        return "showOrder";
//        }
//        else {
//            logger.warn("...unfinished order...");
//            model.addAttribute("success", maktabMessageSource.getEnglish("You.already.have.an.active.order"));
//            return "customerSuccessPage";
//        }
    }


    @PostMapping("/add")
    public ModelAndView addOrder(@ModelAttribute("newOrder") @Validated OrderDto orderDto,
                                 @RequestParam("lat") double lat,
                                 @RequestParam("lon") double lon,
                                 @SessionAttribute("myCustomerDto") CustomerDto customerDto,
                                 HttpServletRequest request) throws ServiceNotFoundException, SubServiceNotFoundException {
        logger.info("...create an order...");
        logger.info("...get address from api");
        //TODO:: put token in property file
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImFkZDliYTFjY2RmMmQ1OWNmOWZiMGRmYjAwNTI5ZDQ3NjcyNzNmZTkyNDA0OWIxZDY2NTUwYTIxNDg0MDQyMWUxYzJkZGY1YTJkYzM5ZGVkIn0.eyJhdWQiOiIxNDYzNCIsImp0aSI6ImFkZDliYTFjY2RmMmQ1OWNmOWZiMGRmYjAwNTI5ZDQ3NjcyNzNmZTkyNDA0OWIxZDY2NTUwYTIxNDg0MDQyMWUxYzJkZGY1YTJkYzM5ZGVkIiwiaWF0IjoxNjI0Nzk4NjAyLCJuYmYiOjE2MjQ3OTg2MDIsImV4cCI6MTYyNzM5MDYwMiwic3ViIjoiIiwic2NvcGVzIjpbImJhc2ljIl19.dEuGOHxMMbShejTdwOmpY6QEAIPchgg7myXUk8m39Oa25GXMF5oaTQf5cE9YX9C-IHEjezF3lsZa-xwG7p9Y2vm9k3eCCAcomSn3FePPoq8abiLMjM3bxas_jw2I2mmsiktEw_jflD5lwF4LU7UCgQsHcA0xW7Vtoef5xzd44NES0GXWUYR4FvSCsd9Ryq2wNy2iAmC0bYs_nTG6tW9Eb3OYSXI_EmOZdY6ZFkjFCAYa3RQ8rGM-SuMQHj1zCNliLLQYYobReVZMRe70RkppSrc2dfES_RrOgr8ZnWvkMoMyinLzcZUM4zVpNGYSWw5XhdqRc0Z02-G5c7cbtjjsOA";
        String uri = "https://map.ir/reverse/no?lat=" + lat + "&" + "lon=" + lon;
        AddressFromMapDto address = getAddressFromRestApi(uri, token).getBody();
        orderDto.setCity(address.getProvince()).setStreet(address.getPrimary()).setAlley(address.getNeighborhood());
        logger.info(address);
        orderDto.setCustomerDto(customerDto);
        customerOrderService.addOrder(orderDto);
        Map<String, String> successMsg = new HashMap<>();
        successMsg.put("success", maktabMessageSource.getEnglish("order.added"));
        return new ModelAndView("customerSuccessPage", successMsg);
    }

    private ResponseEntity<AddressFromMapDto> getAddressFromRestApi(String uri, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("x-api-key", token);
        HttpEntity<AddressFromMapDto> entity = new HttpEntity<>(headers);
        ResponseEntity<AddressFromMapDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, AddressFromMapDto.class);
        return response;
    }


}
