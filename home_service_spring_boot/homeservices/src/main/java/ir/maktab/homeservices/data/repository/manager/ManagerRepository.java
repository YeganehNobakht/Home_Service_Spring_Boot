package ir.maktab.homeservices.data.repository.manager;

import ir.maktab.homeservices.data.entity.Manager;
import ir.maktab.homeservices.dto.ManagerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    Optional<Manager> findByUsernameAndPassword(String username, String password);

    Optional<Manager> findByUsername(String username);

}
