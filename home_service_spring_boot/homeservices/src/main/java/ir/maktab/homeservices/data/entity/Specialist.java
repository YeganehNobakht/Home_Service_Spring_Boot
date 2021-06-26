package ir.maktab.homeservices.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
public class Specialist extends User {
    private double rate;
    @Lob
    @Column(length = 300000, columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "specialist_service",
            joinColumns = {@JoinColumn(name = "specialist_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private Set<ServiceCategory> serviceCategoryList = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "specialist")
    private List<CustomerComment> customerCommentList = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "specialist")
    private List<Suggestion> suggestionList = new ArrayList<>();

//    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "specialist")
//    private List<CustomerOrder> customerOrderList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<CustomerOrder> customerOrderList = new ArrayList<>();
    private Integer commentCounter=0;

    public Specialist(String username, String password, String name, String lastName, String email) {
        super(username, password, name, lastName, email);
    }

    public Specialist() {

    }


    @Override
    public Specialist setId(Integer id) {
        super.setId(id);
        return this;
    }

    public double getRate() {
        return rate;
    }

    public Specialist setRate(double rate) {
        this.rate = rate;
        return this;
    }


    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public Specialist setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Set<ServiceCategory> getServiceCategoryList() {
        return serviceCategoryList;
    }

    public Specialist setServiceCategoryList(Set<ServiceCategory> serviceCategoryList) {
        this.serviceCategoryList = serviceCategoryList;
        return this;
    }

    public List<CustomerComment> getCustomerCommentList() {
        return customerCommentList;
    }

    public Specialist setCustomerCommentList(List<CustomerComment> customerCommentList) {
        this.customerCommentList = customerCommentList;
        return this;
    }

    @Override
    public Specialist setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Specialist setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Specialist setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public Specialist setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }

    @Override
    public Specialist setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Specialist setUserStatus(UserStatus userStatus) {
        super.setUserStatus(userStatus);
        return this;
    }

    @Override
    public Specialist setDate(Date date) {
        super.setDate(date);
        return this;
    }

    @Override
    public Specialist setBalance(double balance) {
        super.setBalance(balance);
        return this;
    }

    public List<Suggestion> getSuggestionList() {
        return suggestionList;
    }

    public Specialist setSuggestionList(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList;
        return this;
    }

    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public Specialist setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
        return this;
    }

    public Integer getCommentCounter() {
        return commentCounter;
    }

    public Specialist setCommentCounter(Integer commentCounter) {
        this.commentCounter = commentCounter;
        return this;
    }
    @Override
    public Specialist setVerificationCode(String verificationCode) {
        super.setVerificationCode(verificationCode);
        return this;
    }

    @Override
    public Specialist setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        return this;
    }
}
