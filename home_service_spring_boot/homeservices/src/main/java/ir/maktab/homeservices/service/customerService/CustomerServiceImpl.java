package ir.maktab.homeservices.service.customerService;

import ir.maktab.homeservices.data.entity.Customer;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.data.repository.Customer.CustomerRepository;
import ir.maktab.homeservices.dto.CustomerDto;
import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.exceptions.checkes.*;
import ir.maktab.homeservices.service.customerOrderService.CustomerOrderService;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.subCategoryService.SubCategoryService;
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
//    private void showServices(){
//        List<ServiceCategory> all = serviceCategoryService.getAll();
//        for (ServiceCategory serviceCategory : all){
//            System.out.println(serviceCategory.getName());
//            serviceCategory.getSubCategoryList().stream()
//                    .filter(s->s.getServiceCategory().equals(serviceCategory.getName()))
//                    .forEach(s-> System.out.println("    - " + s.getName()));
//        }
//    }

    //    private void registerOrder(CustomerDto customerDto) throws Exception {
//        System.out.println("\n\nChose a service:");
//        System.out.println("Example: service/subservice");
//        String[] customerInput = scanner.next().split("/");
//        if (customerInput.length!=2)
//            throw new Exception("Invalid input");
//        ServiceCategory serviceCategory = serviceCategoryService.getByName(customerInput[0]);
//        SubCategory subCategory = subCategoryService.getByName(customerInput[1]);
//
//        System.out.println("Enter your Address");
//        System.out.println("Example: Tehran Valiasr Narges6");
//        String city = scanner.next();
//        String  street = scanner.next();
//        String alley = scanner.next();
//
//        Address address = new Address().setCity(city).setStreet(street).setAlley(alley);
//
//        CustomerOrder customerOrder= new CustomerOrder().setCustomer(customerDto)
//                            .setOrderDate(new Date())
//                            .setAddress(address)
//                            .setServiceCategory(serviceCategory)
//                            .setSubCategory(subCategory)
//                            .setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);
//        customerOrderService.create(customerOrder);
//    }
//
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
