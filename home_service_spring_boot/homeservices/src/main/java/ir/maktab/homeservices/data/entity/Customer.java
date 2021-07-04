package ir.maktab.homeservices.data.entity;

import ir.maktab.homeservices.data.entity.enums.UserStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Customer extends User {
    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerOrder> customerOrderList = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerComment> customerCommentList = new ArrayList<>();


    public Customer(String username, String password, String name, String lastName, String email) {
        super(username, password, name, lastName, email);
    }

    public Customer() {
    }

    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public Customer setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
        return this;
    }

    public List<CustomerComment> getCustomerCommentList() {
        return customerCommentList;
    }

    public Customer setCustomerCommentList(List<CustomerComment> customerCommentList) {
        this.customerCommentList = customerCommentList;
        return this;
    }

    @Override
    public Customer setId(Integer id) {
        super.setId(id);
        return this;
    }

    @Override
    public Customer setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Customer setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Customer setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public Customer setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }

    @Override
    public Customer setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Customer setUserStatus(UserStatus userStatus) {
        super.setUserStatus(userStatus);
        return this;
    }

    @Override
    public Customer setDate(Date date) {
        super.setDate(date);
        return this;
    }

    @Override
    public Customer setBalance(double balance) {
        super.setBalance(balance);
        return this;
    }

    @Override
    public Customer setVerificationCode(String verificationCode) {
        super.setVerificationCode(verificationCode);
        return this;
    }

    @Override
    public Customer setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return this;
    }
}
