package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.dto.enums.UserRole;
import ir.maktab.homeservices.service.validation.*;

import javax.validation.constraints.*;
import java.util.Date;

public class UserDto {

    private Integer id;
    //    @ValidPassword(groups = {OnRegister.class, OnLogin.class, OnChangePassword.class})
    private String username;
    @ValidPassword(groups = {OnRegister.class, OnLogin.class, OnChangePassword.class})
    private String password;
    @NotBlank(message = "name", groups = {OnRegister.class})
    @Size(min = 2, max = 20, message = "name.size", groups = {OnRegister.class})
    private String name;
    @NotBlank(message = "lastName", groups = {OnRegister.class})
    @Size(min = 2, max = 20, message = "lastName.size", groups = {OnRegister.class})
    private String lastName;
    @Email(groups = {OnRegister.class})
    @NotBlank(message = "email", groups = {OnRegister.class})
    private String email;

    private UserStatus userStatus;

    private Date date;
    @NotNull(message = "balance.not.blank", groups = {OnIncreaseBalance.class})
    @Pattern(regexp = "^[0-9]", message = "balance.must.be.int")
    private double Balance;

    private UserRole userRole;

    private String Speciality;

    private String verificationCode;
    private boolean enabled = false;

    public Integer getId() {
        return id;
    }

    public UserDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public UserDto setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public UserDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public Double getBalance() {
        return Balance;
    }

    public UserDto setBalance(Double balance) {
        Balance = balance;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public UserDto setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public UserDto setSpeciality(String speciality) {
        Speciality = speciality;
        return this;
    }

    public UserDto setBalance(double balance) {
        Balance = balance;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public UserDto setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
