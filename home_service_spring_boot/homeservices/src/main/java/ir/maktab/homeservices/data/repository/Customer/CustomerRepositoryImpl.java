package ir.maktab.homeservices.data.repository.Customer;//package ir.maktab.data.repository.Customer;
//
//import ir.maktab.data.entity.Customer;
//import ir.maktab.data.entity.Specialist;
//import ir.maktab.dto.CustomerDto;
//import ir.maktab.dto.SpecialistDto;
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.transform.Transformers;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class CustomerRepositoryImpl implements CustomerRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(Customer customer) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(customer);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(Customer customer) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(customer);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<Customer> get(String id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.Customer as c  where c.username = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<Customer> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<Customer> list =
//                (List<Customer>) session.createQuery("from ir.maktab.data.entity.Customer").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(String id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.Customer as c  where c.uesrname = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public List<CustomerDto> filterCustomer(CustomerDto customerDto) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Criteria criteria = session.createCriteria(Customer.class);
//      if (customerDto.getName()!=null)
//            criteria.add(Restrictions.eq("name",customerDto.getName()));
//        if (customerDto.getLastName()!=null)
//            criteria.add(Restrictions.eq("lastName",customerDto.getLastName()));
//        if (customerDto.getEmail()!=null)
//            criteria.add(Restrictions.eq("email",customerDto.getEmail()));
//        criteria.setResultTransformer(Transformers.aliasToBean(CustomerDto.class));
//        List list = criteria.list();
//        transaction.commit();
//        session.close();
//        return list;
//    }
//
//
//}
