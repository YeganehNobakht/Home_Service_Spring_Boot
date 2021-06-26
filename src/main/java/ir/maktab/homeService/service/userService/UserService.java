package ir.maktab.homeService.service.userService;

import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeService.dto.UserDto;
import ir.maktab.homeService.dto.UserFilter;

import java.util.List;

public interface UserService {

    List<UserDto> filterUser(UserFilter user) throws ServiceNotFoundException;

    List<UserDto> findAll();
}
