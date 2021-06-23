package ir.maktab.service.service.userService;

import ir.maktab.service.dto.UserDto;
import ir.maktab.service.dto.UserFilter;
import ir.maktab.service.exceptions.checkes.ServiceNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> filterUser(UserFilter user) throws ServiceNotFoundException;

    List<UserDto> findAll();
}
