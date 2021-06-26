package ir.maktab.homeService.service.mapper;

import ir.maktab.homeService.data.entity.Manager;
import ir.maktab.homeService.dto.ManagerDto;

public interface ManagerMapper {
    ManagerDto toManagerDto(Manager manager);
    Manager toManager(ManagerDto managerDto);
}
