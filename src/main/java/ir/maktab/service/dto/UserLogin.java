package ir.maktab.service.dto;

/**
 * @author Yeganeh Nobakht
 **/
public class UserLogin {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public UserLogin setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLogin setPassword(String password) {
        this.password = password;
        return this;
    }
}
