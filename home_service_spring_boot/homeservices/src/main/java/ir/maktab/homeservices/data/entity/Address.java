package ir.maktab.homeservices.data.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String alley;


    public Address(String city, String street, String alley) {
        this.city = city;
        this.street = street;
        this.alley = alley;
    }

    public Address() {
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getAlley() {
        return alley;
    }

    public Address setAlley(String alley) {
        this.alley = alley;
        return this;
    }
}
