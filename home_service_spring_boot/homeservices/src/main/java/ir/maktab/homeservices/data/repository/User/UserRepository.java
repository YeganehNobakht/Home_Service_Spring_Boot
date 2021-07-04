package ir.maktab.homeservices.data.repository.User;

import ir.maktab.homeservices.data.entity.User;
import ir.maktab.homeservices.data.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.password=:password where u.id=:id")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    @Modifying
    @Query("update User u set u.userStatus=:userStatus where u.id=:id")
    void updateUserStatus(Integer id, UserStatus userStatus);
}
