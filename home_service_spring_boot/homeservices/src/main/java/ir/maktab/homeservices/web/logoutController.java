package ir.maktab.homeservices.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class logoutController {
    @GetMapping
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "index";
    }
}
