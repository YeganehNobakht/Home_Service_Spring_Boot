package ir.maktab.homeservices.data.entity;

import ir.maktab.homeservices.data.entity.enums.UserStatus;
import ir.maktab.homeservices.dto.enums.UserRole;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_ROLE", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date date;
    private double Balance;
    @Column(name = "USER_ROLE", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String username, String password, String name, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public User setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public User setDate(Date date) {
        this.date = date;
        return this;
    }

    public double getBalance() {
        return Balance;
    }

    public User setBalance(double balance) {
        Balance = balance;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public User setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }
}
