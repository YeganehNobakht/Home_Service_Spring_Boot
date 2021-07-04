package ir.maktab.homeservices.dto;

import javax.persistence.Embeddable;

@Embeddable
public class AddressFromMapDto {
    private String province;
    private String primary;
    private String neighborhood;

    public String getProvince() {
        return province;
    }

    public AddressFromMapDto setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getPrimary() {
        return primary;
    }

    public AddressFromMapDto setPrimary(String primary) {
        this.primary = primary;
        return this;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public AddressFromMapDto setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }
}
