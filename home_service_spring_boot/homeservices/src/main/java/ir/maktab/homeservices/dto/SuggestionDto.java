package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.data.entity.enums.SuggestionStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SuggestionDto {

    private Integer id;
    @NotNull(message = "suggestion.price")
    private Double price;
    private String workDescription;
    @NotNull(message = "suggestion.work.duration")
    private Integer durationOfWork;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    private CustomerOrderDto customerOrder;
    private SpecialistDto specialistDto;
    private SuggestionStatus suggestionStatus;


    public Integer getId() {
        return id;
    }

    public SuggestionDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public SuggestionDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public SuggestionDto setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
        return this;
    }

    public Integer getDurationOfWork() {
        return durationOfWork;
    }

    public SuggestionDto setDurationOfWork(Integer durationOfWork) {
        this.durationOfWork = durationOfWork;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public SuggestionDto setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public CustomerOrderDto getCustomerOrder() {
        return customerOrder;
    }

    public SuggestionDto setCustomerOrder(CustomerOrderDto customerOrder) {
        this.customerOrder = customerOrder;
        return this;
    }

    public SpecialistDto getSpecialistDto() {
        return specialistDto;
    }

    public SuggestionDto setSpecialistDto(SpecialistDto specialistDto) {
        this.specialistDto = specialistDto;
        return this;
    }

    public SuggestionStatus getSuggestionStatus() {
        return suggestionStatus;
    }

    public SuggestionDto setSuggestionStatus(SuggestionStatus suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
        return this;
    }
}
