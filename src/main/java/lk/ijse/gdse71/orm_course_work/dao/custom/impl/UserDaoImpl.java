package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.UserDao;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(User dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(User dto) throws SQLException {
        return false;
    }
    @Override
    public boolean delete(String id) throws SQLException {
        return false;
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
