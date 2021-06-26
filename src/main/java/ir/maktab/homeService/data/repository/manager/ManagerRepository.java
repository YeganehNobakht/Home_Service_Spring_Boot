package ir.maktab.homeService.data.repository.manager;

import ir.maktab.homeService.data.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    Optional<Manager> findByUsernameAndPassword(String username, String password);
//    void create(Manager manager);
//    void update(Manager manager);
//    Optional<Manager> get(Integer id);
//    List<Manager> getAll();
//    void delete(Integer integer);
}
