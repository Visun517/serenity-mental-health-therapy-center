package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.DuplicateException;
import lk.ijse.gdse71.orm_course_work.bo.exception.InvalidCredentialException;
import lk.ijse.gdse71.orm_course_work.bo.exception.MissingFieldException;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;


import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
     UserDto getUser(String userName) throws InvalidCredentialException;
     String getNextId() throws SQLException;
     boolean save(UserDto user) throws SQLException, MissingFieldException, DuplicateException;
     boolean delete(String userId) throws SQLException;
     List<UserDto> getAll() throws SQLException;
     boolean update(UserDto dto) throws SQLException, MissingFieldException, DuplicateException;

}
