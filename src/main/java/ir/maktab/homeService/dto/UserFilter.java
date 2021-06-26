package ir.maktab.homeService.dto;

import ir.maktab.homeService.dto.enums.UserRole;

/**
 * @author Yeganeh Nobakht
 **/
public class UserFilter {
    private UserRole userRole;
    private String name;
    private String lastName;
    private String Email;
    private String speciality;

    public UserRole getUserRole() {
        return userRole;
    }

    public UserFilter setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserFilter setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserFilter setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return Email;
    }

    public UserFilter setEmail(String email) {
        Email = email;
        return this;
    }

    public String getSpeciality() {
        return speciality;
    }

    public UserFilter setSpeciality(String speciality) {
        this.speciality = speciality;
        return this;
    }
}
