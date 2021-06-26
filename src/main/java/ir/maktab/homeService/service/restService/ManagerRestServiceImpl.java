package ir.maktab.homeService.service.restService;

import ir.maktab.homeService.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeService.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeService.service.mapper.UserMapper;
import ir.maktab.homeService.data.entity.CustomerOrder;
import ir.maktab.homeService.data.entity.User;
import ir.maktab.homeService.data.repository.User.UserRepository;
import ir.maktab.homeService.data.repository.customerOrder.CustomerOrderRepository;
import ir.maktab.homeService.data.repository.restSpecification.RestSpecifications;
import ir.maktab.homeService.dto.CustomerOrderDto;
import ir.maktab.homeService.dto.UserDto;
import ir.maktab.homeService.dto.restDto.OrderFilterDto;
import ir.maktab.homeService.dto.restDto.UserOrderDtoFilter;
import ir.maktab.homeService.dto.restDto.UserReportDto;
import ir.maktab.homeService.service.mapper.Mapper;
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
