package ir.maktab.homeservices.service.customerOrderService;

import ir.maktab.homeservices.data.entity.CustomerOrder;
import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import ir.maktab.homeservices.data.repository.customerOrder.CustomerOrderRepository;
import ir.maktab.homeservices.dto.*;
import ir.maktab.homeservices.exceptions.checkes.OrderNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeservices.exceptions.checkes.SubServiceNotFoundException;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.mapper.Mapper;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import ir.maktab.homeservices.service.subCategoryService.SubCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final Mapper mapper;
    private final ServiceCategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final MaktabMessageSource maktabMessageSource;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, Mapper mapper, ServiceCategoryService categoryService, SubCategoryService subCategoryService, MaktabMessageSource maktabMessageSource) {
        this.customerOrderRepository = customerOrderRepository;
        this.mapper = mapper;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.maktabMessageSource = maktabMessageSource;
    }

    @Override
    public void addOrder(OrderDto orderDto) throws ServiceNotFoundException, SubServiceNotFoundException {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();

        ServiceCategoryDto serviceByName = categoryService.getByName(orderDto.getServiceCategory());
        SubCategoryDto subServiceByName = subCategoryService.getByName(orderDto.getSubCategory());
        AddressDto addressDto = new AddressDto().setAlley(orderDto.getAlley())
                .setCity(orderDto.getCity()).setStreet(orderDto.getStreet());

//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(orderDto.getWorkDate());
        customerOrderDto.setServiceCategory(serviceByName)
                .setSubCategory(subServiceByName)
                .setJobDescription(orderDto.getJobDescription())
                .setWorkDate(orderDto.getWorkDate())
                .setAddressDto(addressDto)
                .setCustomerDto(orderDto.getCustomerDto())
                .setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_OFFER);

        CustomerOrder customerOrder = mapper.toCustomerOrder(customerOrderDto);
        customerOrderRepository.save(customerOrder.setSpecialist(null));
    }

    @Override
    public List<CustomerOrderDto> findByService(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException {
        ServiceCategoryDto service = categoryService.getByName(serviceCategoryDto.getName());
        List<CustomerOrder> customerOrder = customerOrderRepository
                .findByServiceCategoryAndWorkDateGreaterThanEqual(mapper.toServiceCategory(service),new Date());
        return customerOrder.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
    }

    @Override
    public CustomerOrderDto findById(Integer orderId) throws OrderNotFoundException {
        Optional<CustomerOrder> order = customerOrderRepository.findById(orderId);
        if (order.isPresent())
            return mapper.toCustomerOrderDto(order.get());
        throw new OrderNotFoundException(maktabMessageSource.getEnglish("order.not.found",new Object[]{orderId}));
    }

    @Override
    public List<CustomerOrderDto> findByCustomer(CustomerDto customerDto) {
        List<CustomerOrder> customerOrders = customerOrderRepository.findByCustomerAndOrderStatusNot(mapper.toCustomer(customerDto), OrderStatus.PAID);

        return customerOrders.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
    }
    @Override
    public List<CustomerOrderDto> findByCustomerAndOrderStatusNotPaid(CustomerDto customerDto) {
        List<CustomerOrder> customerOrders = customerOrderRepository.findByCustomerAndOrderStatusNot(mapper.toCustomer(customerDto), OrderStatus.PAID);

        return customerOrders.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<CustomerOrderDto> findByOrderStatusNotAndCustomer_Id(OrderStatus orderStatus, Integer customerId) {
        List<CustomerOrder> customerOrderList = customerOrderRepository.findByOrderStatusNotAndCustomer_Id(orderStatus, customerId);
        return customerOrderList.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateOrderStatus(CustomerOrderDto orderDto) throws OrderNotFoundException {
        if (customerOrderRepository.findById(orderDto.getId()).isPresent()) {
            //using save method for update
            customerOrderRepository.updateOrderStatus(orderDto.getId(),orderDto.getOrderStatus());
        } else
            throw new OrderNotFoundException(maktabMessageSource.getEnglish("order.not.found",new Object[]{orderDto.getId()}));
    }
    @Transactional
    @Override
    public void updateOrderStatusAndPriceAndSpecialist(CustomerOrderDto orderDto) throws OrderNotFoundException {
        if (customerOrderRepository.findById(orderDto.getId()).isPresent()) {
            customerOrderRepository.updateOrderStatusAndPriceAndSpecialist(orderDto.getId(),orderDto.getOrderStatus(),orderDto.getPrice(),mapper.toSpecialist(orderDto.getSpecialistDto()));
        } else
            throw new OrderNotFoundException(maktabMessageSource.getEnglish("order.not.found",new Object[]{orderDto.getId()}));
    }

    @Override
    public List<CustomerOrderDto> findUserByStatusAndCustomer(OrderStatus orderStatus, CustomerDto customerDto) throws OrderNotFoundException {
        List<CustomerOrder> customerOrder = customerOrderRepository.findUserByStatusAndCustomer(orderStatus, customerDto.getId());
        if (customerOrder.size()!=0){
            return customerOrder.stream().map(mapper::toCustomerOrderDto).collect(Collectors.toList());
        }
        throw new OrderNotFoundException((maktabMessageSource.getEnglish("order.not.found2",new Object[]{orderStatus,customerDto.getUsername()})));
    }
    @Transactional
    @Override
    public void updateComment(Integer orderId, CustomerCommentDto customerCommentDto) {
        customerOrderRepository.updateComment(orderId,mapper.toCustomerComment(customerCommentDto));
    }


}
