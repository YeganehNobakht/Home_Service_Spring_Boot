package ir.maktab.homeservices.data.repository.manager;//package ir.maktab.data.repository.manager;
//
//
//import ir.maktab.data.entity.Manager;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class ManagerRepositoryImpl implements ManagerRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public ManagerRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(Manager manager) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(manager);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(Manager manager) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(manager);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<Manager> get(Integer id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.Manager as c  where c.username = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<Manager> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<Manager> list =
//                (List<Manager>) session.createQuery("from ir.maktab.data.entity.Manager").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.Manager as c  where c.uesrname = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//}
