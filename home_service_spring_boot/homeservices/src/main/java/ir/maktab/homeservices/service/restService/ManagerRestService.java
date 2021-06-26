package ir.maktab.homeservices.service.restService;

import ir.maktab.homeservices.dto.CustomerOrderDto;
import ir.maktab.homeservices.dto.UserDto;
import ir.maktab.homeservices.dto.restDto.OrderFilterDto;
import ir.maktab.homeservices.dto.restDto.UserOrderDtoFilter;
import ir.maktab.homeservices.dto.restDto.UserReportDto;
import ir.maktab.homeservices.exceptions.checkes.UserNotFoundException;

import java.util.List;


public interface ManagerRestService {

    List<CustomerOrderDto> filterAUserOrders(UserOrderDtoFilter dto) throws UserNotFoundException;
    List<CustomerOrderDto> filterAllOrders(OrderFilterDto dto);
    List<UserDto> userFilter(UserReportDto dto);
}
