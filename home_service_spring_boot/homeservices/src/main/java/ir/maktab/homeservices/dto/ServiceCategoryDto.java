package ir.maktab.homeservices.dto;


import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryDto {

    private Integer id;
    @NotBlank(message = "name")
    private String name;

    private List<SubCategoryDto> subCategoryList = new ArrayList<>();

    private List<CustomerOrderDto> customerOrderList = new ArrayList<>();

    private List<SpecialistDto> specialistList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public ServiceCategoryDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceCategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<SubCategoryDto> getSubCategoryList() {
        return subCategoryList;
    }

    public ServiceCategoryDto setSubCategoryList(List<SubCategoryDto> subCategoryList) {
        this.subCategoryList = subCategoryList;
        return this;
    }

    public List<CustomerOrderDto> getCustomerOrderList() {
        return customerOrderList;
    }

    public ServiceCategoryDto setCustomerOrderList(List<CustomerOrderDto> customerOrderList) {
        this.customerOrderList = customerOrderList;
        return this;
    }

    public List<SpecialistDto> getSpecialistList() {
        return specialistList;
    }

    public ServiceCategoryDto setSpecialistList(List<SpecialistDto> specialistList) {
        this.specialistList = specialistList;
        return this;
    }
}
