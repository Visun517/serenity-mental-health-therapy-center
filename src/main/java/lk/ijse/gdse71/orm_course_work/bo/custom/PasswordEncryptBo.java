package lk.ijse.gdse71.orm_course_work.bo.custom;

import lk.ijse.gdse71.orm_course_work.bo.SuperBo;

public interface PasswordEncryptBo extends SuperBo {
    public String encryptPassword(String password);
    public boolean checkedPassword(String normalPassword, String encodedPassword);

}
