package ir.maktab.homeService.web;

import ir.maktab.homeService.dto.CustomerOrderDto;
import ir.maktab.homeService.dto.UserDto;
import ir.maktab.homeService.dto.restDto.ApiErrorDto;
import ir.maktab.homeService.dto.restDto.OrderFilterDto;
import ir.maktab.homeService.dto.restDto.UserOrderDtoFilter;
import ir.maktab.homeService.dto.restDto.UserReportDto;
import ir.maktab.homeService.exceptions.checkes.UserNotFoundException;
import ir.maktab.homeService.service.restService.ManagerRestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/managerRestController")
public class ManagerRestController {
    private final ManagerRestService managerRestService;

    public ManagerRestController(ManagerRestService managerRestService) {
        this.managerRestService = managerRestService;
    }


    @PostMapping(value = "/filterOrderForUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> filterOrdersForAUser(@RequestBody @Valid UserOrderDtoFilter dto) throws UserNotFoundException {
        List<CustomerOrderDto> orderDtoList = managerRestService.filterAUserOrders(dto);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/filterOrders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> filterOrders(@RequestBody OrderFilterDto dto){
        List<CustomerOrderDto> orderDtoList = managerRestService.filterAllOrders(dto);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/filterUsers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> filterUsers(@RequestBody UserReportDto dto){
        List<UserDto> userDtoList = managerRestService.userFilter(dto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Object> bindExceptionHandler(BindException ex) {
        List<String> validationErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        });
        ApiErrorDto dto = new ApiErrorDto(HttpStatus.BAD_REQUEST, ex.getClass().getName(), validationErrors);
        return new ResponseEntity<>(dto, dto.getStatus());
    }

}
