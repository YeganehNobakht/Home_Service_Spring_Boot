package ir.maktab.homeservices.data.repository.subCategory;//package ir.maktab.data.repository.subCategory;
//
//
//import ir.maktab.data.entity.SubCategory;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class SubCategoryRepositoryImpl implements SubCategoryRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public SubCategoryRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(SubCategory subCategory) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(subCategory);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(SubCategory subCategory) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(subCategory);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<SubCategory> get(Integer id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.SubCategory as c  where c.id = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public Optional<SubCategory> getByName(String name) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.SubCategory as c  where c.name = :c_name")
//                .setParameter("c_name",name);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<SubCategory> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<SubCategory> list =
//                (List<SubCategory>) session.createQuery("from ir.maktab.data.entity.SubCategory").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.SubCategory as c  where c.id = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//}
