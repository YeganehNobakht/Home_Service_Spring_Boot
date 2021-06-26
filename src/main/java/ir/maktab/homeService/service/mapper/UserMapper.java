package ir.maktab.homeService.service.mapper;

import ir.maktab.homeService.data.entity.User;
import ir.maktab.homeService.dto.UserDto;

/**
 * @author Yeganeh Nobakht
 **/
public interface UserMapper {

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
