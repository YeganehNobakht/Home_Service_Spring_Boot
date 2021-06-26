package ir.maktab.homeservices.data.repository.serviceCategory;//package ir.maktab.data.repository.serviceCategory;
//
//
//import ir.maktab.data.entity.ServiceCategory;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class ServiceCategoryRepositoryImpl implements ServiceCategoryRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public ServiceCategoryRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(ServiceCategory serviceCategory) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(serviceCategory);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(ServiceCategory serviceCategory) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(serviceCategory);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<ServiceCategory> get(Integer id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.ServiceCategory as c  where c.id = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public Optional<ServiceCategory> getByName(String name) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.ServiceCategory as c  where c.name = :c_name")
//                .setParameter("c_name",name);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<ServiceCategory> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<ServiceCategory> list =
//                (List<ServiceCategory>) session.createQuery("from ir.maktab.data.entity.ServiceCategory").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.ServiceCategory as c  where c.id = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//}
