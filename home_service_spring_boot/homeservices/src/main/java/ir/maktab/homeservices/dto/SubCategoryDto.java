package ir.maktab.homeservices.dto;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryDto {

    private Integer id;
    @NotBlank(message = "name")
    private String name;

    private ServiceCategoryDto serviceCategory;

    private double price;

    public double getPrice() {
        return price;
    }

    public SubCategoryDto setPrice(double price) {
        this.price = price;
        return this;
    }

    private List<CustomerOrderDto> customerOrderList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public SubCategoryDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubCategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    public ServiceCategoryDto getServiceCategory() {
        return serviceCategory;
    }

    public SubCategoryDto setServiceCategory(ServiceCategoryDto serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public List<CustomerOrderDto> getCustomerOrderList() {
        return customerOrderList;
    }

    public SubCategoryDto setCustomerOrderList(List<CustomerOrderDto> customerOrderList) {
        this.customerOrderList = customerOrderList;
        return this;
    }


}
