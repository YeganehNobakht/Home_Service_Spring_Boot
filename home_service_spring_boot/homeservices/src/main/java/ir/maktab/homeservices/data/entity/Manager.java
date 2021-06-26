package ir.maktab.homeservices.data.entity;

import javax.persistence.*;

@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;

    public Manager() {
    }

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Manager setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Manager setPassword(String password) {
        this.password = password;
        return this;
    }
}
