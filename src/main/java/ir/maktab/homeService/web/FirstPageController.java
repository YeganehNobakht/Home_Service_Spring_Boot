package ir.maktab.homeService.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
