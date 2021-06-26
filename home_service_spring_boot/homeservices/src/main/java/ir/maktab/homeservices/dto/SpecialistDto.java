package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.dto.enums.UserRole;

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

    private List<SuggestionDto> suggestionList = new ArrayList<>();


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

    @Override
    public SpecialistDto setSpeciality(String speciality) {
        super.setSpeciality(speciality);
        return this;
    }

    @Override
    public SpecialistDto setBalance(double balance) {
        super.setBalance(balance);
        return this;
    }

    public List<SuggestionDto> getSuggestionList() {
        return suggestionList;
    }

    public SpecialistDto setSuggestionList(List<SuggestionDto> suggestionList) {
        this.suggestionList = suggestionList;
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
    @Override
    public SpecialistDto setVerificationCode(String verificationCode) {
        super.setVerificationCode(verificationCode);
        return this;
    }

    @Override
    public SpecialistDto setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return this;
    }
}
