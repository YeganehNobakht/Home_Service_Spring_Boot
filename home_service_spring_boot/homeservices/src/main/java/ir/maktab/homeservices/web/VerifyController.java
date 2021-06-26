package ir.maktab.homeservices.web;

import ir.maktab.homeservices.service.userService.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/verify")

public class VerifyController {
    private final UserService userService;

    public VerifyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{code}")
    public String verifyUser(@PathVariable("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
