package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.data.entity.CustomerOrder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class CustomerCommentDto {
    private Integer id;
    private String comment;
    @NotNull(message = "rate.null")
    @Range(min = 1, max = 5, message = "rate.size")
    private Double score;
    private CustomerDto customerDto;
    private SpecialistDto specialistDto;
    private CustomerOrder customerOrder;

    public Integer getId() {
        return id;
    }

    public CustomerCommentDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CustomerCommentDto setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Double getScore() {
        return score;
    }

    public CustomerCommentDto setScore(Double score) {
        this.score = score;
        return this;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public CustomerCommentDto setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
        return this;
    }

    public SpecialistDto getSpecialistDto() {
        return specialistDto;
    }

    public CustomerCommentDto setSpecialistDto(SpecialistDto specialistDto) {
        this.specialistDto = specialistDto;
        return this;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public CustomerCommentDto setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        return this;
    }
}
