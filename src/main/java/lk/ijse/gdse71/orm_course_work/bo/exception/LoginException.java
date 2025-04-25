package lk.ijse.gdse71.orm_course_work.bo.exception;

public class LoginException extends CustomeException{
    public LoginException(String message) {
        super(message);
    }
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
