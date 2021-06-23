package ir.maktab.service.service.restService;

import ir.maktab.service.dto.CustomerOrderDto;
import ir.maktab.service.dto.UserDto;
import ir.maktab.service.dto.restDto.OrderFilterDto;
import ir.maktab.service.dto.restDto.UserOrderDtoFilter;
import ir.maktab.service.dto.restDto.UserReportDto;
import ir.maktab.service.exceptions.checkes.UserNotFoundException;

import java.util.List;


public interface ManagerRestService {

    List<CustomerOrderDto> filterAUserOrders(UserOrderDtoFilter dto) throws UserNotFoundException;
    List<CustomerOrderDto> filterAllOrders(OrderFilterDto dto);
    List<UserDto> userFilter(UserReportDto dto);
}
