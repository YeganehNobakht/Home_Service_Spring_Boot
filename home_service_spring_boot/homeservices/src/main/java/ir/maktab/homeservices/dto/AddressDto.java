package ir.maktab.homeservices.dto;

import javax.validation.constraints.NotBlank;

public class AddressDto {
    @NotBlank(message = "city.not.blank")
    private String city;
    @NotBlank(message = "street.not.blank")
    private String street;
    @NotBlank(message = "alley.not.blank")
    private String alley;

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getAlley() {
        return alley;
    }

    public AddressDto setAlley(String alley) {
        this.alley = alley;
        return this;
    }
}
