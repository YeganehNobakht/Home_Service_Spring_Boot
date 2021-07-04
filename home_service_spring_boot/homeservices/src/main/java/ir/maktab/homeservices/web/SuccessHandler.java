package ir.maktab.homeservices.web;


import ir.maktab.homeservices.dto.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        HttpSession session = request.getSession(true);
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        session.setAttribute("loginUsername", userDetail.getUsername());
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("role " + grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().equals("ROLE_" + UserRole.Customer.name())) {

                redirectUrl = "/customer/register";
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_" + UserRole.Specialist.name())) {
                redirectUrl = "/specialist/register";
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_" + UserRole.Manager.name())) {
                redirectUrl = "/mngr/register";
                break;
            }
        }
        System.out.println("redirectUrl " + redirectUrl);
        if (redirectUrl == null) {
            throw new IllegalStateException();
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
