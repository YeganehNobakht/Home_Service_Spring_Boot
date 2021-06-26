package ir.maktab.homeservices.data.repository.customerOrder;

import ir.maktab.homeservices.data.entity.*;
import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer>, JpaSpecificationExecutor<CustomerOrder> {
//    void create(CustomerOrder customerOrder);
//    void update(CustomerOrder customerOrder);
//    Optional<CustomerOrder> get(Integer id);
//    List<CustomerOrder> getAll();
//    void delete(Integer integer);



    List<CustomerOrder> findByServiceCategoryAndWorkDateGreaterThanEqual(ServiceCategory serviceCategory, Date date);
    List<CustomerOrder> findByCustomerAndOrderStatusNot(Customer customer, OrderStatus orderStatus);
    @Query("SELECT c FROM CustomerOrder c WHERE c.orderStatus = :status and c.customer.id = :customerId")
    List<CustomerOrder> findUserByStatusAndCustomer(@Param("status") OrderStatus orderStatus, @Param("customerId") Integer customerId);

    @Modifying
    @Query("update CustomerOrder o set o.orderStatus = :orderStatus where o.id = :id")
    void updateOrderStatus(@Param("id") Integer id, @Param("orderStatus") OrderStatus orderStatus);

    @Modifying
    @Query("update CustomerOrder o set o.orderStatus = :orderStatus,o.price=:price,o.specialist=:specialist where o.id = :id")
    void updateOrderStatusAndPriceAndSpecialist(@Param("id") Integer id, @Param("orderStatus") OrderStatus orderStatus, @Param("price")double price, @Param("specialist") Specialist specialist);

    List<CustomerOrder> findByOrderStatusNotAndCustomer_Id(OrderStatus orderStatus, Integer customerId);

    @Modifying
    @Query("update CustomerOrder  o set o.customerComment=:customerComment where o.id = :id")
    void updateComment(@Param("id") Integer orderId, @Param("customerComment") CustomerComment customerComment);
}
