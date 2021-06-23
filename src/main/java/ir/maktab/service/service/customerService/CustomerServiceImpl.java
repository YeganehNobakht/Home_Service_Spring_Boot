package ir.maktab.service.service.customerService;

import ir.maktab.service.data.entity.Customer;
import ir.maktab.service.data.entity.enums.UserStatus;
import ir.maktab.service.data.repository.Customer.CustomerRepository;
import ir.maktab.service.dto.CustomerDto;
import ir.maktab.service.dto.CustomerOrderDto;
import ir.maktab.service.exceptions.checkes.*;
import ir.maktab.service.service.customerOrderService.CustomerOrderService;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.mapper.Mapper;
import ir.maktab.service.service.serviceCategory.ServiceCategoryService;
import ir.maktab.service.service.subCategoryService.SubCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ServiceCategoryService serviceCategoryService,
                               SubCategoryService subCategoryService,
                               CustomerOrderService customerOrderService,
                               Mapper mapper, MaktabMessageSource maktabMessageSource) {
        this.customerRepository = customerRepository;
        this.serviceCategoryService = serviceCategoryService;
        this.subCategoryService = subCategoryService;
        this.customerOrderService = customerOrderService;
        this.mapper = mapper;
        this.maktabMessageSource = maktabMessageSource;
    }

    @Override
    public void update(CustomerDto customerDto) throws CustomerNotFoundException {
        if (customerRepository.findById(customerDto.getId()).isPresent()) {
            //using save method for update
            customerRepository.save(mapper.toCustomer(customerDto));
        } else
            throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found",new Object[]{customerDto.getUsername()}));
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
                //using save method for update
                return customerRepository.save(customer.get());
            }
            throw new PasswordNotFoundException(maktabMessageSource.getEnglish("password.not.found"));

        }
        throw new CustomerNotFoundException(maktabMessageSource.getEnglish("customer.not.found",new Object[]{username}));
    }

    @Override
    public CustomerDto create(CustomerDto customerDto) throws DuplicateEmailException, DuplicateUsernameException {

        Customer customer = mapper.toCustomer(customerDto);
        Optional<Customer> customer1 = customerRepository.findByEmail(customer.getEmail());
        if (customer1.isPresent()) {
            throw new DuplicateEmailException(maktabMessageSource.getEnglish("duplicate.email"));

        } else {
            Optional<Customer> customer2 = customerRepository.findByUsername(customer.getUsername());
            if (customer2.isPresent())
                throw new DuplicateUsernameException(maktabMessageSource.getEnglish("duplicate.username"));
            else
                customer.setUserStatus(UserStatus.WAITING);
                return mapper.toCustomerDto(customerRepository.save(customer));
        }
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
        double newBalance = customerDto.getBalance()-price;
        customerRepository.updateBalance(customerDto.getId(),newBalance);
    }
    @Transactional
    @Override
    public void updateBalance(Double balance,Integer id) {
        customerRepository.updateBalance(id,balance);
    }


}
