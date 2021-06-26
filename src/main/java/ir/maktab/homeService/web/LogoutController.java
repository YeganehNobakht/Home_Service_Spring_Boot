package ir.maktab.homeService.web;

import ir.maktab.homeService.service.maktabMassageSource.MaktabMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final Logger logger = LogManager.getLogger(LogoutController.class);
    private final MaktabMessageSource maktabMessageSource;

    public LogoutController(MaktabMessageSource maktabMessageSource) {
        this.maktabMessageSource = maktabMessageSource;
    }

    @GetMapping
    public String logout(HttpServletRequest request){
        logger.info(maktabMessageSource.getEnglish("success.logout"));
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "index";
    }
}
