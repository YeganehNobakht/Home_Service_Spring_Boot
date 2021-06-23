package ir.maktab.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Yeganeh Nobakht
 **/

public class OrderDto {
    @NotBlank(message = "blank")
    private String serviceCategory;
    @NotBlank(message = "blank")
    private String  subCategory;

    private String jobDescription;
    @NotNull(message = "blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    @NotBlank(message = "city.not.blank")
    private String city;
    @NotBlank(message = "street.not.blank")
    private String street;
    @NotBlank(message = "alley.not.blank")
    private String alley;
    private CustomerDto customerDto;
    private SpecialistDto specialistDto;


    public SpecialistDto getSpecialistDto() {
        return specialistDto;
    }

    public OrderDto setSpecialistDto(SpecialistDto specialistDto) {
        this.specialistDto = specialistDto;
        return this;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public OrderDto setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public OrderDto setSubCategory(String subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public OrderDto setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public OrderDto setWorkDate(Date workDate) {
        this.workDate = workDate;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OrderDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public OrderDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getAlley() {
        return alley;
    }

    public OrderDto setAlley(String alley) {
        this.alley = alley;
        return this;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public OrderDto setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
        return this;
    }
}
