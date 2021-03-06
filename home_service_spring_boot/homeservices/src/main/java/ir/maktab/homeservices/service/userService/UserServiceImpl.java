package ir.maktab.homeservices.service.userService;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.data.repository.User.UserRepository;
import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.UserFilter;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.service.mapper.UserMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(UserDto dto, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = dto.getEmail();
        String fromAddress = "masimasi128@gmail.com";
        String senderName = "masi";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "zahra asgari...";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", dto.getName());
        String verifyURL = siteURL + "/verify/" + dto.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            if (user.getUserRole().equals(UserRole.Customer))
                user.setUserStatus(UserStatus.APPROVE);
            if (user.getUserRole().equals(UserRole.Specialist))
                user.setUserStatus(UserStatus.WAITING);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public List<UserDto> filterUser(UserFilter user, ServiceCategory service) throws ServiceNotFoundException {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.addAll(
                userRepository.findAll(UserRepository.filterUsers(user, service))
                        .stream().map(userMapper::toUserDto).collect(Collectors.toList())
        );

        return userDtoList;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> all = userRepository.findAll();
        return all.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }
}
