package ir.maktab.homeservices.dto;

import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.dto.enums.UserRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDto extends UserDto {
    private List<CustomerOrderDto> customerOrderList = new ArrayList<>();

    private List<CustomerCommentDto> customerCommentList = new ArrayList<>();

    public List<CustomerOrderDto> getCustomerOrderList() {
        return customerOrderList;
    }

    public CustomerDto setCustomerOrderList(List<CustomerOrderDto> customerOrderList) {
        this.customerOrderList = customerOrderList;
        return this;
    }

    public List<CustomerCommentDto> getCustomerCommentList() {
        return customerCommentList;
    }

    public CustomerDto setCustomerCommentList(List<CustomerCommentDto> customerCommentList) {
        this.customerCommentList = customerCommentList;
        return this;
    }

    @Override
    public CustomerDto setId(Integer id) {
        super.setId(id);
        return this;
    }

    @Override
    public CustomerDto setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public CustomerDto setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public CustomerDto setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public CustomerDto setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }

    @Override
    public CustomerDto setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public CustomerDto setUserStatus(UserStatus userStatus) {
        super.setUserStatus(userStatus);
        return this;
    }

    @Override
    public CustomerDto setDate(Date date) {
        super.setDate(date);
        return this;
    }

    @Override
    public CustomerDto setBalance(Double balance) {
        super.setBalance(balance);
        return this;
    }

    @Override
    public CustomerDto setUserRole(UserRole userRole) {
        super.setUserRole(userRole);
        return this;
    }
}
