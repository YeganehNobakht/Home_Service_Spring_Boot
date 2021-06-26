package ir.maktab.homeservices.service.mapper;

import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.dto.UserDto;

/**
 * @author Yeganeh Nobakht
 **/
public interface UserMapper {

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
