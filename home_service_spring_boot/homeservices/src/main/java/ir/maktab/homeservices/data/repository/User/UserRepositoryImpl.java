package ir.maktab.homeservices.data.repository.User;//package ir.maktab.data.repository.User;
//
//import ir.maktab.data.entity.User;
//import ir.maktab.dto.UserDto;
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.transform.Transformers;
//
//import java.util.List;
//
//public class UserRepositoryImpl implements UserRepository {
//    private final SessionFactory sessionFactory;
//
//    public UserRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public List<UserDto> filterUser(UserDto userDto) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Criteria criteria = session.createCriteria(User.class);
//        if (userDto.getUserRole()!=null)
//            criteria.add(Restrictions.eq("userRole",userDto.getUserRole()));
//        if (userDto.getName()!=null)
//            criteria.add(Restrictions.eq("name",userDto.getName()));
//        if (userDto.getLastName()!=null)
//            criteria.add(Restrictions.eq("lastName",userDto.getLastName()));
//        if (userDto.getEmail()!=null)
//            criteria.add(Restrictions.eq("email",userDto.getEmail()));
//        criteria.setResultTransformer(Transformers.aliasToBean(UserDto.class));
//        List list = criteria.list();
//        transaction.commit();
//        session.close();
//        return list;
//    }
//}
