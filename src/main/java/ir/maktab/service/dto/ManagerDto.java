package ir.maktab.service.dto;

import ir.maktab.service.service.validation.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ManagerDto {
    @NotBlank(message = "userName")
    @Size(min = 8, max = 12, message = "manager.userName")
    private String username;
    @ValidPassword
    private String password;

    public String getUsername() {
        return username;
    }

    public ManagerDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ManagerDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
