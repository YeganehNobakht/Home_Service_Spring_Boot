package ir.maktab.homeService.data.repository.specialist;

import ir.maktab.homeService.data.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {
//    void create(Specialist specialist);
//    void update(Specialist specialist);
//    Optional<Specialist> get(String id);
//    List<Specialist> getAll();
//    void delete(String  username);
//    List<SpecialistDto> filterSpecialist(SpecialistDto specialistDto);



    Optional<Specialist> findByEmailAndUsername(String email,String username);
    Optional<Specialist> findByEmail(String email);
    Optional<Specialist> findByUsername(String username);

    Optional<Specialist> findByUsernameAndPassword(String username, String Password);

    @Modifying
    @Query("update Specialist s set s.rate = :rate,s.commentCounter=:counter where s.id = :id")
    void updateRateAndCommentCounter(@Param("id") Integer id, @Param("rate") Double score, @Param("counter")Integer counter);

    @Modifying
    @Query("update Specialist s set s.Balance = :balance where s.id = :id")
    void updateBalance(@Param("id") Integer id, @Param("balance") double balance);

//    @Modifying
//    @Query("update Specialist s set s.serviceCategoryList = :service where s.id = :id")
//    void updateService(@Param("id") Integer id, @Param("service") Set<ServiceCategory> service);
}
