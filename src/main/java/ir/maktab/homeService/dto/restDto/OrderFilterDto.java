package ir.maktab.homeService.dto.restDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.homeService.data.entity.enums.OrderStatus;

import java.util.Date;

public class OrderFilterDto {
    private String serviceName;
    private String subServiceName;
    private OrderStatus orderStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date endDate;

    public String getServiceName() {
        return serviceName;
    }

    public OrderFilterDto setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public OrderFilterDto setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderFilterDto setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public OrderFilterDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public OrderFilterDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }
}
