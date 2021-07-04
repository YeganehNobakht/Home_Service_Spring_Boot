package ir.maktab.homeservices.service.mapper;

import ir.maktab.homeservices.data.entity.Manager;
import ir.maktab.homeservices.dto.ManagerDto;

public interface ManagerMapper {
    ManagerDto toManagerDto(Manager manager);

    Manager toManager(ManagerDto managerDto);
}
