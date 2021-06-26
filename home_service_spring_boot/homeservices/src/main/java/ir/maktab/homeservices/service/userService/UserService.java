package ir.maktab.homeservices.service.userService;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.UserFilter;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    void sendVerificationEmail(UserDto dto, String siteURL)
            throws UnsupportedEncodingException, MessagingException;

    boolean verify(String verificationCode);

    List<UserDto> filterUser(UserFilter user, ServiceCategory serviceCategory) throws ServiceNotFoundException;

    List<UserDto> findAll();
}
