package ir.maktab.service.service.mapper;

import ir.maktab.service.data.entity.Manager;
import ir.maktab.service.dto.ManagerDto;

public interface ManagerMapper {
    ManagerDto toManagerDto(Manager manager);
    Manager toManager(ManagerDto managerDto);
}
