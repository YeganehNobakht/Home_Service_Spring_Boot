package ir.maktab.homeService.dto;

import ir.maktab.homeService.dto.enums.UserRole;
import ir.maktab.homeService.data.entity.enums.UserStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecialistDto extends UserDto {
    @Size(min = 5, max = 40, message = "rate.size")
    private double rate;
    @NotNull(message = "image")
    private byte[] profilePicture;
    private Integer commentCounter = 0;

    private List<ServiceCategoryDto> serviceCategoryList = new ArrayList<>();

    private List<CustomerCommentDto> customerCommentList = new ArrayList<>();

    private List<SuggestionDto> suggestionDtoList = new ArrayList<>();


    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public SpecialistDto setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<ServiceCategoryDto> getServiceCategoryList() {
        return serviceCategoryList;
    }

    public SpecialistDto setServiceCategoryList(List<ServiceCategoryDto> serviceCategoryList) {
        this.serviceCategoryList = serviceCategoryList;
        return this;
    }

    public List<SuggestionDto> getSuggestionDtoList() {
        return suggestionDtoList;
    }

    public SpecialistDto setSuggestionDtoList(List<SuggestionDto> suggestionDtoList) {
        this.suggestionDtoList = suggestionDtoList;
        return this;
    }

    public List<CustomerCommentDto> getCustomerCommentList() {
        return customerCommentList;
    }

    public SpecialistDto setCustomerCommentList(List<CustomerCommentDto> customerCommentList) {
        this.customerCommentList = customerCommentList;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public SpecialistDto setRate(double rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public SpecialistDto setId(Integer id) {
        super.setId(id);
        return this;
    }

    @Override
    public SpecialistDto setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public SpecialistDto setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public SpecialistDto setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public SpecialistDto setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }

    @Override
    public SpecialistDto setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public SpecialistDto setUserStatus(UserStatus userStatus) {
        super.setUserStatus(userStatus);
        return this;
    }

    @Override
    public SpecialistDto setDate(Date date) {
        super.setDate(date);
        return this;
    }

    @Override
    public SpecialistDto setUserRole(UserRole userRole) {
        super.setUserRole(userRole);
        return this;
    }

    @Override
    public SpecialistDto setBalance(Double balance) {
        super.setBalance(balance);
        return this;
    }

    public Integer getCommentCounter() {
        return commentCounter;
    }

    public SpecialistDto setCommentCounter(Integer commentCounter) {
        this.commentCounter = commentCounter;
        return this;
    }
}
