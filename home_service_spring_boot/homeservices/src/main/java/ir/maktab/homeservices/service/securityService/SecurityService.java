package ir.maktab.homeservices.service.securityService;

public interface SecurityService {

    void autoLogin(String username, String password);

    String findLoggedInUsername();
}
