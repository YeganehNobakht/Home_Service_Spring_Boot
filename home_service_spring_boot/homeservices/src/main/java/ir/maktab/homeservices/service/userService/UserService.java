package ir.maktab.homeservices.service.userService;

import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.UserFilter;
import ir.maktab.homeservices.exceptions.checkes.ServiceNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> filterUser(UserFilter user) throws ServiceNotFoundException;

    List<UserDto> findAll();
}
