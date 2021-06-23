package ir.maktab.service.service.customerOrderService;

import ir.maktab.service.data.entity.enums.OrderStatus;
import ir.maktab.service.dto.*;
import ir.maktab.service.exceptions.checkes.OrderNotFoundException;
import ir.maktab.service.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.service.exceptions.checkes.SubServiceNotFoundException;

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
