package ir.maktab.service.service.restService;

import ir.maktab.service.data.entity.CustomerOrder;
import ir.maktab.service.data.entity.User;
import ir.maktab.service.data.repository.User.UserRepository;
import ir.maktab.service.data.repository.customerOrder.CustomerOrderRepository;
import ir.maktab.service.data.repository.restSpecification.RestSpecifications;
import ir.maktab.service.dto.CustomerOrderDto;
import ir.maktab.service.dto.UserDto;
import ir.maktab.service.dto.restDto.OrderFilterDto;
import ir.maktab.service.dto.restDto.UserOrderDtoFilter;
import ir.maktab.service.dto.restDto.UserReportDto;
import ir.maktab.service.exceptions.checkes.UserNotFoundException;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.service.service.mapper.Mapper;
import ir.maktab.service.service.mapper.UserMapper;
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
        throw new UserNotFoundException(maktabMessageSource.getEnglish("user.not.found",new Object[]{dto.getUserId()}));
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
