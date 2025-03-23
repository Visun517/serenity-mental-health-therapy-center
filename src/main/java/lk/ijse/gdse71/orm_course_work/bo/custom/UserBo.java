package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBo extends SuperBo {
     User getUser(String userName);
     String getNextId() throws SQLException;
     boolean save(UserDto user) throws SQLException;
     boolean delete(String userId) throws SQLException;
     List<UserDto> getAll() throws SQLException;
     public boolean update(UserDto dto) throws SQLException;

}
