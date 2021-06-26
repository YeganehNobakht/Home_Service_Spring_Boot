package ir.maktab.homeservices.data.repository.customerOrder;//package ir.maktab.data.repository.customerOrder;
//
//import ir.maktab.data.entity.CustomerOrder;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class CustomerOrderRepositoryImpl implements CustomerOrderRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public CustomerOrderRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(CustomerOrder customerOrder) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(customerOrder);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(CustomerOrder customerOrder) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(customerOrder);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<CustomerOrder> get(Integer id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.CustomerOrder as c  where c.id = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<CustomerOrder> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<CustomerOrder> list =
//                (List<CustomerOrder>) session.createQuery("from ir.maktab.data.entity.CustomerOrder").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.CustomerOrder as c  where c.id = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//}
