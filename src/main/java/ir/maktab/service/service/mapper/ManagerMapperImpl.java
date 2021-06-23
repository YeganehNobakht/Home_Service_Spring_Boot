package ir.maktab.service.service.mapper;

import ir.maktab.service.data.entity.Manager;
import ir.maktab.service.dto.ManagerDto;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapperImpl implements ManagerMapper {
    @Override
    public ManagerDto toManagerDto(Manager manager) {
        return new ManagerDto()
                .setPassword(manager.getPassword())
                .setUsername(manager.getUsername());
    }

    @Override
    public Manager toManager(ManagerDto managerDto) {
        return new Manager()
                .setPassword(managerDto.getPassword())
                .setUsername(managerDto.getUsername());
    }
}
