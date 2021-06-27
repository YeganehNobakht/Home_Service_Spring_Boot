package ir.maktab.homeservices.service.customerService;

import ir.maktab.homeservices.data.entity.Customer;
import ir.maktab.homeservices.data.repository.Customer.CustomerRepository;
import ir.maktab.homeservices.dto.CustomerDto;
import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.subCategoryService.SubCategoryService;
import ir.maktab.homeservices.service.userService.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ServiceCategoryService serviceCategoryService;
    private final SubCategoryService subCategoryService;
    private final CustomerOrderService customerOrderService;
    private final Mapper mapper;
    private final MaktabMessageSource maktabMessageSource;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ServiceCategoryService serviceCategoryService,
                               SubCategoryService subCategoryService,
                               CustomerOrderService customerOrderService,
                               Mapper mapper, MaktabMessageSource maktabMessageSource, PasswordEncoder passwordEncoder, UserService userService) {
        this.customerRepository = customerRepository;
        this.serviceCategoryService = serviceCategoryService;
        this.subCategoryService = subCategoryService;
        this.customerOrderService = customerOrderService;
        this.mapper = mapper;
        this.maktabMessageSource = maktabMessageSource;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void update(CustomerDto customerDto) throws CustomerNotFoundException {
        if (customerRepository.findById(customerDto.getId()).isPresent()) {
            //using save method for update
            customerRepository.save(mapper.toCustomer(customerDto));
        } else
            throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found", new Object[]{customerDto.getUsername()}));
    }


    private Customer fetchCustomerByUsername(String username) throws Exception {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return customer.get();
        } else
            throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found", new Object[]{username}));
    }

    @Override
    public Customer changePassword(String username, String oldPass, String newPass) throws CustomerNotFoundException, PasswordNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            if (customer.get().getPassword().equals(oldPass)) {
                customer.get().setPassword(newPass);
                return customerRepository.save(customer.get());
            }
            throw new PasswordNotFoundException(maktabMessageSource.getEnglish("password.not.found"));

        }
        throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found", new Object[]{username}));
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto, String siteURL) throws UnsupportedEncodingException, MessagingException, DuplicateEmailException, DuplicateUsernameException {
        Customer customer = mapper.toCustomer(customerDto);
        findDuplicateEmail(customerDto.getEmail());
        findDuplicateUsername(customerDto.getUsername());

        //TODO:: method
        String encodedPassword = passwordEncoder.encode(customerDto.getPassword());
        customer.setPassword(encodedPassword);
        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode).setEnabled(false);
        customerDto.setVerificationCode(randomCode).setEnabled(false);

        customerRepository.save(customer);
        userService.sendVerificationEmail(customerDto, siteURL);
        return customerDto;

    }


    @Override
    public CustomerDto findDuplicateEmail(String email) throws DuplicateEmailException {
        Optional<Customer> customer1 = customerRepository.findByEmail(email);
        if (customer1.isPresent())
            throw new DuplicateEmailException(maktabMessageSource.getEnglish("duplicate.email"));
        return null;

    }

    @Override
    public CustomerDto findDuplicateUsername(String username) throws DuplicateUsernameException {
        Optional<Customer> customer1 = customerRepository.findByUsername(username);
        if (customer1.isPresent())
            throw new DuplicateUsernameException(maktabMessageSource.getEnglish("duplicate.username"));
        return null;

    }

    @Override
    public CustomerDto login(CustomerDto customerDto) throws PasswordNotFoundException, UserNameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(customerDto.getUsername());
        if (customer.isPresent()) {
            if (customer.get().getPassword().equals(customerDto.getPassword())) {
                return mapper.toCustomerDto(customer.get());
            }
            throw new PasswordNotFoundException(maktabMessageSource.getEnglish("login.password.not.found"));
        }
        throw new UserNameNotFoundException(maktabMessageSource.getEnglish("username.not.found"));
    }

    @Override
    public List<CustomerOrderDto> getOrders(CustomerDto customerDto) {
        return customerOrderService.findByCustomer(customerDto);
    }


    @Transactional
    @Override
    public void payByAccount(CustomerDto customerDto, double price) {
        double newBalance = customerDto.getBalance() - price;
        customerRepository.updateBalance(customerDto.getId(), newBalance);
    }

    @Transactional
    @Override
    public void updateBalance(Double balance, Integer id) {
        customerRepository.updateBalance(id, balance);
    }

    @Override
    public CustomerDto findByUsername(String loggedInUsername) throws CustomerNotFoundException {
        Optional<Customer> byUsername = customerRepository.findByUsername(loggedInUsername);
        if (byUsername.isPresent()) {
            return mapper.toCustomerDto(byUsername.get());
        } else
            throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found", new Object[]{loggedInUsername}));
    }


}
