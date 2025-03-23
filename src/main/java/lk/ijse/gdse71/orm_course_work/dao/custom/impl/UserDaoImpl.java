package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.UserDao;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("select u.user_id from User u order by u.user_id desc", String.class).setMaxResults(1).uniqueResult();
        return lastId;
    }

    @Override
    public List<User> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        List<User> fromCustomer = session.createQuery("from User", User.class).list();
        ArrayList<User> customerArrayList = new ArrayList<>();
        customerArrayList.addAll(fromCustomer);

        return customerArrayList;
    }

    @Override
    public boolean save(User user) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean update(User dto) throws SQLException {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(dto);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }
    @Override
    public boolean delete(String userId) throws SQLException {
       Session session = factoryConfiguration.getSession();
       Transaction transaction = session.beginTransaction();

       try{
           User user = session.get(User.class, userId);
           if (user != null){
               session.remove(user);
               transaction.commit();
               return true;
           }else{
               System.out.println("User is null");
               return false;
           }


       }catch (Exception e){
           e.printStackTrace();
           return false;
       }finally {
           if (session != null){
               session.close();
           }

       }
    }

    @Override
    public User getUser(String userName) {
        Session session = factoryConfiguration.getSession();
        try {
            String sql = "from User u where u.username = :userName";
            Query<User> query = session.createQuery(sql, User.class);
            query.setParameter("userName",userName);
            return query.uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }
}
