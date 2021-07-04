package ir.maktab.homeservices.web;

import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.service.validation.OnLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Yeganeh Nobakht
 **/
@Controller
@RequestMapping("/")
public class FirstPageController {
    private final Logger logger = LogManager.getLogger(FirstPageController.class);

    @GetMapping
    public String home() {
        logger.info("...show first page when application is started...");
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        logger.info("...login page...");
        return new ModelAndView("login", "userDto", new UserDto());
    }

    @PostMapping("/userLogin")
    public ModelAndView Login(@ModelAttribute("userDto") @Validated(OnLogin.class) UserDto userDto) {
        logger.info("...login page...");
        return new ModelAndView("customerService", "userDto", new UserDto());
    }

    @GetMapping("/accessdenied")
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "denied";
    }
}
