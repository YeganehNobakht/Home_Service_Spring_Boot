package ir.maktab.homeservices.service.userService;

import ir.maktab.homeservices.data.entity.ServiceCategory;
import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.data.repository.User.UserRepository;
import ir.maktab.homeservices.data.repository.specification.Specifications;
import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.UserFilter;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.exceptions.checkes.PasswordNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.UserMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final MaktabMessageSource maktabMessageSource;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JavaMailSender mailSender, PasswordEncoder passwordEncoder, MaktabMessageSource maktabMessageSource) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.maktabMessageSource = maktabMessageSource;
    }

    @Override
    public void sendVerificationEmail(UserDto dto, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String toAddress = dto.getEmail();
        String fromAddress = "masimasi128@gmail.com";
        String senderName = "Bama";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Best regard,<br>"
                + "Bama service...";

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
        List<User> all = userRepository.findAll(Specifications.filterUsers(user, service));
        all.removeIf(u -> u.getUserRole().equals(UserRole.Manager));
        return new ArrayList<>(all.stream().map(userMapper::toUserDto).collect(Collectors.toList()));
    }

    @Override
    public List<UserDto> findAll() {
        List<User> all = userRepository.findAll();
        all.removeIf(user -> user.getUserRole().equals(UserRole.Manager));

        return all.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void checkForChangePassword(UserDto userDto, String oldPass, String newPass) throws PasswordNotFoundException {
        String encodeOldPass = passwordEncoder.encode(oldPass);
        String encodeNewPass = passwordEncoder.encode(newPass);
        boolean matches = passwordEncoder.matches(oldPass, userDto.getPassword());
        if (matches)
            userRepository.updatePassword(userDto.getId(), encodeNewPass);
        else
            throw new PasswordNotFoundException(maktabMessageSource.getEnglish("password.not.found"));

    }

    @Transactional
    @Override
    public void confirmeUser(Integer id) throws UserNotFoundException {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent())
            userRepository.updateUserStatus(id, UserStatus.APPROVE);
        else
            throw new UserNotFoundException(maktabMessageSource.getEnglish("user.not.found", new Object[]{id}));
    }
}
