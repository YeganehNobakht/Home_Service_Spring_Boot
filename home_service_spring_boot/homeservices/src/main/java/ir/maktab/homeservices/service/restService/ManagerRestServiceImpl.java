package ir.maktab.homeservices.service.restService;

import ir.maktab.homeservices.data.entity.CustomerOrder;
import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.data.repository.User.UserRepository;
import ir.maktab.homeservices.data.repository.customerOrder.CustomerOrderRepository;
import ir.maktab.homeservices.data.repository.specification.RestSpecifications;
import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.restDto.OrderFilterDto;
import ir.maktab.homeservices.dto.restDto.UserOrderDtoFilter;
import ir.maktab.homeservices.dto.restDto.UserReportDto;
import ir.maktab.homeservices.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.mapper.UserMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerRestServiceImpl implements ManagerRestService {
    private final CustomerOrderRepository customerOrderRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final MaktabMessageSource maktabMessageSource;
    private final UserMapper userMapper;

    public ManagerRestServiceImpl(CustomerOrderRepository customerOrderRepository, Mapper mapper, UserRepository userRepository, MaktabMessageSource maktabMessageSource, UserMapper userMapper) {
        this.customerOrderRepository = customerOrderRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.maktabMessageSource = maktabMessageSource;
        this.userMapper = userMapper;
    }

    @Override
    public List<CustomerOrderDto> filterAUserOrders(UserOrderDtoFilter dto) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isPresent()) {
            Specification<CustomerOrder> customerOrderSpecification = RestSpecifications.filterAUserOrders(dto, user.get().getUserRole());
            List<CustomerOrder> all = customerOrderRepository.findAll(customerOrderSpecification);
            return all.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
        }
        throw new UserNotFoundException(maktabMessageSource.getEnglish("user.not.found", new Object[]{dto.getUserId()}));
    }

    @Override
    public List<CustomerOrderDto> filterAllOrders(OrderFilterDto dto) {
        List<CustomerOrder> orderList = customerOrderRepository.findAll(RestSpecifications.filterOrders(dto));
        return orderList.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> userFilter(UserReportDto dto) {
        List<User> userList = userRepository.findAll(RestSpecifications.userReportFilter(dto));
        return userList.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

}
