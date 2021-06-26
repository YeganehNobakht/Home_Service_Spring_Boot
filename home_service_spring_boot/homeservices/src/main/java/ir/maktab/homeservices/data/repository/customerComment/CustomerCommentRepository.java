package ir.maktab.homeservices.data.repository.customerComment;

import ir.maktab.homeservices.data.entity.CustomerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCommentRepository extends JpaRepository<CustomerComment,Integer> {

}
