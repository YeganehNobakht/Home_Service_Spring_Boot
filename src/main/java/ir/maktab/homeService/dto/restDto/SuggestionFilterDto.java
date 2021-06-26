package ir.maktab.homeService.dto.restDto;

import ir.maktab.homeService.dto.enums.UserRole;
import ir.maktab.homeService.data.entity.enums.SuggestionStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class  SuggestionFilterDto {
    private Integer id;
    private UserRole role;
    private SuggestionStatus suggestionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    private Date endDate;
    private Double maxSuggestionPrice;
    private Double minSuggestionPrice;

    public Integer getId() {
        return id;
    }

    public SuggestionFilterDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public SuggestionFilterDto setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public SuggestionStatus getSuggestionStatus() {
        return suggestionStatus;
    }

    public SuggestionFilterDto setSuggestionStatus(SuggestionStatus suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public SuggestionFilterDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public SuggestionFilterDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Double getMaxSuggestionPrice() {
        return maxSuggestionPrice;
    }

    public SuggestionFilterDto setMaxSuggestionPrice(Double maxSuggestionPrice) {
        this.maxSuggestionPrice = maxSuggestionPrice;
        return this;
    }

    public Double getMinSuggestionPrice() {
        return minSuggestionPrice;
    }

    public SuggestionFilterDto setMinSuggestionPrice(Double minSuggestionPrice) {
        this.minSuggestionPrice = minSuggestionPrice;
        return this;
    }
}
