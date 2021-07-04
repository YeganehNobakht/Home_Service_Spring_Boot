package ir.maktab.homeservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.homeservices.data.entity.enums.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerOrderDto {
    private Integer id;
    private OrderStatus orderStatus;
    private ServiceCategoryDto serviceCategory;
    private SubCategoryDto subCategory;
    private String jobDescription;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date orderDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    private AddressDto addressDto;
    private CustomerDto customerDto;
    private SuggestionDto acceptedSuggestionDto;
    private SpecialistDto specialistDto;
    private double price;
    private CustomerCommentDto customerCommentDto;
    private AddressFromMapDto addressFromMapDto;


    public Integer getId() {
        return id;
    }

    public CustomerOrderDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public CustomerOrderDto setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public ServiceCategoryDto getServiceCategory() {
        return serviceCategory;
    }

    public CustomerOrderDto setServiceCategory(ServiceCategoryDto serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public SubCategoryDto getSubCategory() {
        return subCategory;
    }

    public CustomerOrderDto setSubCategory(SubCategoryDto subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public CustomerOrderDto setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public CustomerOrderDto setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public CustomerOrderDto setWorkDate(Date workDate) {
        this.workDate = workDate;
        return this;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public CustomerOrderDto setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
        return this;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public CustomerOrderDto setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
        return this;
    }

    public SuggestionDto getAcceptedSuggestionDto() {
        return acceptedSuggestionDto;
    }

    public CustomerOrderDto setAcceptedSuggestionDto(SuggestionDto acceptedSuggestionDto) {
        this.acceptedSuggestionDto = acceptedSuggestionDto;
        return this;
    }

    public SpecialistDto getSpecialistDto() {
        return specialistDto;
    }

    public CustomerOrderDto setSpecialistDto(SpecialistDto specialistDto) {
        this.specialistDto = specialistDto;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public CustomerOrderDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public CustomerCommentDto getCustomerCommentDto() {
        return customerCommentDto;
    }

    public CustomerOrderDto setCustomerCommentDto(CustomerCommentDto customerCommentDto) {
        this.customerCommentDto = customerCommentDto;
        return this;
    }

    public AddressFromMapDto getAddressFromMapDto() {
        return addressFromMapDto;
    }

    public CustomerOrderDto setAddressFromMapDto(AddressFromMapDto addressFromMapDto) {
        this.addressFromMapDto = addressFromMapDto;
        return this;
    }
}
