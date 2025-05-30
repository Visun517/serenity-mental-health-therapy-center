package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PasswordEncryptBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.UserBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.DuplicateException;
import lk.ijse.gdse71.orm_course_work.bo.exception.InvalidCredentialException;
import lk.ijse.gdse71.orm_course_work.bo.exception.MissingFieldException;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.UserDao;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.entity.User;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private final UserDao userDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.USER);
    private final PasswordEncryptBo passwordEncryptBo = BoFactory.getInstance().getBo(BoFactory.BOType.PASSWORD);

    @Override
    public UserDto getUser(String userName) throws InvalidCredentialException {

        User user = userDao.getUser(userName);

        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUser_id(user.getUser_id());
            userDto.setPassword(user.getPassword());
            userDto.setUsername(user.getUsername());
            userDto.setRole(user.getRole());
            return userDto;
        }
        throw new InvalidCredentialException("Invalid username or password");
    }

    @Override
    public String getNextId() throws SQLException {
        String id = userDao.getNextId();
        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("A%03d", newIdIndex);
        }
        return "A001";
    }

    @Override
    public boolean save(UserDto userDto) throws SQLException, MissingFieldException, DuplicateException {

        if (userDto.getPassword().isEmpty() && userDto.getUsername().isEmpty() && userDto.getUser_id().isEmpty() && userDto.getRole().isEmpty()) {
            throw new MissingFieldException("Missing fields");
        }

        if (userDao.getUser(userDto.getUsername()) != null) {
            throw new DuplicateException("Duplicate user");
        }
        User user = new User();
        user.setUser_id(userDto.getUser_id());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncryptBo.encryptPassword(userDto.getPassword()));
        user.setRole(userDto.getRole());

        return userDao.save(user);
    }

    @Override
    public boolean delete(String userId) throws SQLException {
        return userDao.delete(userId);
    }

    @Override
    public List<UserDto> getAll() throws SQLException {
        List<User> users = userDao.getAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setUser_id(user.getUser_id());
            userDto.setUsername(user.getUsername());
            userDto.setRole(user.getRole());

            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public boolean update(UserDto dto) throws SQLException, MissingFieldException, DuplicateException {
        if (dto.getPassword().isEmpty() && dto.getUsername().isEmpty() && dto.getUser_id().isEmpty() && dto.getRole().isEmpty()) {
            throw new MissingFieldException("Missing fields");
        }

        if (userDao.getUser(dto.getUsername()) != null) {
            throw new DuplicateException("Duplicate user");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setUser_id(dto.getUser_id());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncryptBo.encryptPassword(dto.getPassword()));

        return userDao.update(user);
    }
}
