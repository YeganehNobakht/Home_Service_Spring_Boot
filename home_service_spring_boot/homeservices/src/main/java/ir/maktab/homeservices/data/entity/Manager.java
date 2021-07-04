package ir.maktab.homeservices.data.entity;

import javax.persistence.Entity;

@Entity
public class Manager extends User {


    public Manager() {
    }

    @Override
    public Manager setUsername(String username) {
        super.setUsername(username);
        return this;
    }

    @Override
    public Manager setPassword(String password) {
        super.setPassword(password);
        return this;
    }
}
