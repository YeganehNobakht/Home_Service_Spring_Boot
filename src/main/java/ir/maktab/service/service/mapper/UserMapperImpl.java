package ir.maktab.service.service.mapper;

import ir.maktab.service.data.entity.User;
import ir.maktab.service.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * @author Yeganeh Nobakht
 **/

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDto userDto) {
        return new User()
                .setId(userDto.getId())
                .setName(userDto.getName())
                .setLastName(userDto.getLastName())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .setUserStatus(userDto.getUserStatus())
                .setUserRole(userDto.getUserRole());
    }

    @Override
    public UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setUserStatus(user.getUserStatus())
                .setUserRole(user.getUserRole());
    }
}
