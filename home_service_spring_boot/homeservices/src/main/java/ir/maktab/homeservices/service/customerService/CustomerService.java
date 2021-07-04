package ir.maktab.homeservices.service.customerService;

import ir.maktab.homeservices.data.entity.Customer;
import ir.maktab.homeservices.dto.CustomerDto;
import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.exceptions.checkes.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CustomerService {

    void update(CustomerDto customerDto) throws CustomerNotFoundException;

    void changePassword(CustomerDto customerDto, String oldPass, String newPass) throws CustomerNotFoundException, PasswordNotFoundException;

    CustomerDto registerCustomer(CustomerDto customerDto, String siteURL) throws UnsupportedEncodingException, MessagingException, DuplicateEmailException, DuplicateUsernameException;

    CustomerDto findDuplicateEmail(String email) throws DuplicateEmailException;

    CustomerDto findDuplicateUsername(String username) throws DuplicateUsernameException;

    CustomerDto login(CustomerDto customerDto) throws PasswordNotFoundException, UserNameNotFoundException;

    List<CustomerOrderDto> getOrders(CustomerDto customerDto) throws Exception;


    void payByAccount(CustomerDto customerDto, double price);

    void updateBalance(Double balance, Integer id);

    CustomerDto findByUsername(String loggedInUsername) throws CustomerNotFoundException;
}
