package ir.maktab.homeService.service.customerService;

import ir.maktab.homeService.data.entity.Customer;
import ir.maktab.homeService.dto.CustomerDto;
import ir.maktab.homeService.dto.CustomerOrderDto;
import ir.maktab.homeService.exceptions.checkes.*;

import java.util.List;

public interface CustomerService {

    void update(CustomerDto customerDto) throws CustomerNotFoundException;

    Customer changePassword(String username, String oldPass, String newPass) throws CustomerNotFoundException, PasswordNotFoundException;

    CustomerDto create(CustomerDto customerOrderDto) throws DuplicateEmailException, DuplicateUsernameException;

    CustomerDto login(CustomerDto customerDto) throws PasswordNotFoundException, UserNameNotFoundException;

    List<CustomerOrderDto> getOrders(CustomerDto customerDto) throws Exception;



    void payByAccount(CustomerDto customerDto, double price);

    void updateBalance(Double balance,Integer id);
}
