package ir.maktab.homeservices.data.repository.specialist;//package ir.maktab.data.repository.specialist;
//
//import ir.maktab.data.entity.Specialist;
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
//public class SpecialistRepositoryImpl implements SpecialistRepository {
//
//
//    private final SessionFactory sessionFactory;
//
//    public SpecialistRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(Specialist specialist) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.persist(specialist);
//        tx.commit();
//        session.close();
//    }
//
//    @Override
//    public void update(Specialist specialist) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.update(specialist);
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public Optional<Specialist> get(String id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        javax.persistence.Query query  = session.createQuery("from ir.maktab.data.entity.Specialist as c  where c.uername = :c_id")
//                .setParameter("c_id",id);
//        Optional f =  query.getResultList().stream().findFirst();
//        transaction.commit();
//        session.close();
//        return f;
//    }
//
//    @Override
//    public List<Specialist> getAll() {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        List<Specialist> list =
//                (List<Specialist>) session.createQuery("from ir.maktab.data.entity.Specialist").list();
//        tx.commit();
//        session.close();
//        return list;
//    }
//
//    @Override
//    public void delete(String  id) {
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from ir.maktab.data.entity.Specialist as c  where c.username = :c_id")
//                .setParameter("c_id",id).executeUpdate();
//        tx.commit();
//        session.close();
//
//    }
//
//    @Override
//    public List<SpecialistDto> filterSpecialist(SpecialistDto specialistDto) {
////        Session session = sessionFactory.openSession();
////        Transaction transaction = session.beginTransaction();
////        Criteria criteria = session.createCriteria(Specialist.class,"s");
////        criteria.createAlias("s.serviceCategoryList","c");
////
////        if (specialistDto.getServiceCategoryList().size()!=0)
////            criteria.add(Restrictions.in("c.","s.serviceCategoryList"));
////        if (specialistDto.getName()!=null)
////            criteria.add(Restrictions.eq("s.name",specialistDto.getName()));
////        if (specialistDto.getLastName()!=null)
////            criteria.add(Restrictions.eq("s.lastName",specialistDto.getLastName()));
////        if (specialistDto.getEmail()!=null)
////            criteria.add(Restrictions.eq("s.email",specialistDto.getEmail()));
////        criteria.setResultTransformer(Transformers.aliasToBean(SpecialistDto.class));
////        List list = criteria.list();
////        transaction.commit();
////        session.close();
////        return list;
//
//        return null;
//    }
//}
