package ir.maktab.homeService.web;

import ir.maktab.homeService.exceptions.checkes.OrderNotFoundException;
import ir.maktab.homeService.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeService.service.customerService.CustomerService;
import ir.maktab.homeService.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeService.service.specialistService.SpecialistService;
import ir.maktab.homeService.data.entity.enums.OrderStatus;
import ir.maktab.homeService.dto.CustomerDto;
import ir.maktab.homeService.dto.CustomerOrderDto;
import ir.maktab.homeService.dto.PaymentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Yeganeh Nobakht
 **/
@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final Logger logger = LogManager.getLogger(PaymentController.class);
    private final CustomerOrderService customerOrderService;
    private final CustomerService customerService;
    private final MaktabMessageSource maktabMessageSource;
    private final SpecialistService specialistService;

    public PaymentController(CustomerOrderService customerOrderService, CustomerService customerService, MaktabMessageSource maktabMessageSource, SpecialistService specialistService) {
        this.customerOrderService = customerOrderService;
        this.customerService = customerService;
        this.maktabMessageSource = maktabMessageSource;
        this.specialistService = specialistService;
    }

    @GetMapping("/byCard")
    public ModelAndView payByCard(){
        logger.info("...payment by card...");
        return new ModelAndView("payment","paymentDto",new PaymentDto());
    }

    @GetMapping("/byBalance")
    public String payByAccount(@SessionAttribute("myCustomerDto") CustomerDto customerDto,
                               @SessionAttribute("price") double price, Model model){
        logger.info("...payment by account balance...");
        //TODO
        if (customerDto.getBalance()<price){
            logger.warn("...low balance...");
            model.addAttribute("message",maktabMessageSource.getEnglish("account.balance.low"));
            return "customerPaymentInformations";
        }
        customerService.payByAccount(customerDto,price);
        model.addAttribute("success",maktabMessageSource.getEnglish("tanks.for.payment"));
        return ("customerSuccessPage");
    }

  @PostMapping("/complete")
  public String save(@ModelAttribute("paymentDto") @Valid PaymentDto paymentDto,
                     @SessionAttribute("price")double price,
                     @SessionAttribute("customerOrderDto")CustomerOrderDto customerOrderDto,
                     @SessionAttribute("myCustomerDto") CustomerDto customerDto,
                     HttpSession session, HttpServletRequest request,
                     Model model) throws OrderNotFoundException {
      logger.info("...complete payment transaction...");
        String captcha = session.getAttribute("captcha_security").toString();
        String verifyCaptcha = request.getParameter("captcha");
        if (captcha.equals(verifyCaptcha)) {
            model.addAttribute("success", maktabMessageSource.getEnglish("transaction.commit"));
            customerOrderService.updateOrderStatus(customerOrderDto.setOrderStatus(OrderStatus.PAID));

            specialistService.returnMoney(price,customerOrderDto.getSpecialistDto());
            return "customerSuccessPage";
        } else {
            model.addAttribute("error", maktabMessageSource.getEnglish("captcha.invalid"));
            return "payment";
        }

    }

    @GetMapping("/timeout")
    public String redirect(){
        logger.warn("...payment timeout...");
        return "timeOut";
    }
    @PostMapping("/increaseBalance")
    public String increaseBalance(@SessionAttribute("balance")double balance,
                                  @SessionAttribute("myCustomerDto")CustomerDto customerDto, Model model){
        logger.info("....pay by account balance....");
        customerDto.setBalance(customerDto.getBalance()+balance);
        customerService.updateBalance(customerDto.getBalance(),customerDto.getId());
        model.addAttribute("money",maktabMessageSource.getEnglish("balance.increase"));
        return "customerSuccessPage";
    }
    @GetMapping("/balanceTimeout")
    public String balanceTimeout(){
        logger.warn("...payment timeout...");
        return "balanceTimeout";
    }
}
