package ir.maktab.homeService.dto.restDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.homeService.data.entity.enums.OrderStatus;

import java.util.Date;

public class UserOrderDtoFilter {
    private Integer userId;
    private OrderStatus orderStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date endDate;
    private Double maxPrice;
    private Double minPrice;

    public Integer getUserId() {
        return userId;
    }

    public UserOrderDtoFilter setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public UserOrderDtoFilter setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public UserOrderDtoFilter setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public UserOrderDtoFilter setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public UserOrderDtoFilter setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public UserOrderDtoFilter setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
        return this;
    }
}
