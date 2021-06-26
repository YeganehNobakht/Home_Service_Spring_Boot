package ir.maktab.homeService.service.customerOrderService;

import ir.maktab.homeService.dto.*;
import ir.maktab.homeService.exceptions.checkes.OrderNotFoundException;
import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeService.exceptions.checkes.SubServiceNotFoundException;
import ir.maktab.homeService.data.entity.enums.OrderStatus;

import java.util.List;

public interface CustomerOrderService {
    void addOrder(OrderDto orderDto) throws ServiceNotFoundException, SubServiceNotFoundException;

    List<CustomerOrderDto> findByService(ServiceCategoryDto serviceCategoryDto) throws ServiceNotFoundException;
    CustomerOrderDto findById(Integer orderId) throws OrderNotFoundException;
    List<CustomerOrderDto> findByCustomer(CustomerDto customerDto);
    List<CustomerOrderDto> findByOrderStatusNotAndCustomer_Id(OrderStatus orderStatus , Integer customerId);
    void updateOrderStatus(CustomerOrderDto orderDto) throws OrderNotFoundException;
    void updateOrderStatusAndPriceAndSpecialist(CustomerOrderDto orderDto) throws OrderNotFoundException;
    List<CustomerOrderDto> findUserByStatusAndCustomer(OrderStatus orderStatus, CustomerDto customerDto) throws OrderNotFoundException;
    void updateComment(Integer orderId, CustomerCommentDto customerCommentDto);
}
