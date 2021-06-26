package ir.maktab.homeService.service.restService;

import ir.maktab.homeService.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeService.dto.CustomerOrderDto;
import ir.maktab.homeService.dto.UserDto;
import ir.maktab.homeService.dto.restDto.OrderFilterDto;
import ir.maktab.homeService.dto.restDto.UserOrderDtoFilter;
import ir.maktab.homeService.dto.restDto.UserReportDto;

import java.util.List;


public interface ManagerRestService {

    List<CustomerOrderDto> filterAUserOrders(UserOrderDtoFilter dto) throws UserNotFoundException;
    List<CustomerOrderDto> filterAllOrders(OrderFilterDto dto);
    List<UserDto> userFilter(UserReportDto dto);
}
