package ir.maktab.homeService.dto;

import ir.maktab.homeService.service.validation.OnChangePassword;
import ir.maktab.homeService.service.validation.OnLogin;
import ir.maktab.homeService.service.validation.OnRegister;
import ir.maktab.homeService.service.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Yeganeh Nobakht
 **/
public class SpecialistSignUpDto {
    @NotBlank(message = "username.blank", groups = {OnRegister.class})
    @Pattern(regexp="^[a-zA-Z0-9]{3}",message="username")
    private String username;
    @ValidPassword(groups = {OnRegister.class, OnLogin.class, OnChangePassword.class})
    private String password;
    @NotBlank(message = "name", groups = {OnRegister.class})
    @Size(min = 2, max = 20, message = "name.size", groups = {OnRegister.class})
    private String name;
    @NotBlank(message = "lastName", groups = {OnRegister.class})
    @Size(min = 2, max = 20, message = "lastName.size", groups = {OnRegister.class})
    private String lastName;
    @Email(groups = { OnRegister.class})
    @NotBlank(message = "email", groups = {OnRegister.class})
    private String email;
    private String  balance;
    private ServiceCategoryDto serviceCategory;
    private List<ServiceCategoryDto> serviceCategoryDtoList;
    private byte[] profilePicture;


    public String getUsername() {
        return username;
    }

    public SpecialistSignUpDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SpecialistSignUpDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public SpecialistSignUpDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SpecialistSignUpDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SpecialistSignUpDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBalance() {
        return balance;
    }

    public SpecialistSignUpDto setBalance(String balance) {
        this.balance = balance;
        return this;
    }

    public ServiceCategoryDto getServiceCategory() {
        return serviceCategory;
    }

    public SpecialistSignUpDto setServiceCategory(ServiceCategoryDto serviceCategory) {
        this.serviceCategory = serviceCategory;
        return this;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public SpecialistSignUpDto setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<ServiceCategoryDto> getServiceCategoryDtoList() {
        return serviceCategoryDtoList;
    }

    public void addService(ServiceCategoryDto serviceCategoryDto) {
        serviceCategoryDtoList.add(serviceCategoryDto);

    }
}
