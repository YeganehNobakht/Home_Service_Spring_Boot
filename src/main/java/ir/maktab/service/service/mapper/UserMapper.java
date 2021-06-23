package ir.maktab.service.service.mapper;

import ir.maktab.service.data.entity.User;
import ir.maktab.service.dto.UserDto;

/**
 * @author Yeganeh Nobakht
 **/
public interface UserMapper {

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
