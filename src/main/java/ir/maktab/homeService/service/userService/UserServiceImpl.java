package ir.maktab.homeService.service.userService;


import ir.maktab.homeService.data.entity.ServiceCategory;
import ir.maktab.homeService.data.entity.User;
import ir.maktab.homeService.data.repository.User.UserRepository;
import ir.maktab.homeService.dto.ServiceCategoryDto;
import ir.maktab.homeService.dto.UserDto;
import ir.maktab.homeService.dto.UserFilter;
import ir.maktab.homeService.exceptions.checkes.ServiceNotFoundException;
import ir.maktab.homeService.service.mapper.Mapper;
import ir.maktab.homeService.service.mapper.UserMapper;
import ir.maktab.homeService.service.serviceCategory.ServiceCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Mapper mapper;
    private final ServiceCategoryService serviceCategoryService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, Mapper mapper, ServiceCategoryService serviceCategoryService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mapper = mapper;
        this.serviceCategoryService = serviceCategoryService;
    }


    @Override
    @Deprecated
    public List<UserDto> filterUser(UserFilter user) throws ServiceNotFoundException {
        List<UserDto> userDtoList = new ArrayList<>();
        ServiceCategoryDto serviceCategoryDto =null;
        if (!StringUtils.isEmpty(user.getSpeciality())) {
            serviceCategoryDto = serviceCategoryService.getByName(user.getSpeciality());
        }
        ServiceCategory service = null;
        if (serviceCategoryDto!=null){
            service = mapper.toServiceCategory(serviceCategoryDto);
        }
        userDtoList.addAll(
                userRepository.findAll(UserRepository.filterUsers(user,service))
                        .stream().map(userMapper::toUserDto).collect(Collectors.toList())
        );

//        userDtoList.addAll(
//                userRepository.findAll(UserRepository.filterSpecialists(user))
//                        .stream().map(userMapper::toUserDto).collect(Collectors.toList())
//        );

        return userDtoList;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> all = userRepository.findAll();
        return all.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }
}
