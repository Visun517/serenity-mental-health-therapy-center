package lk.ijse.gdse71.orm_course_work.bo.exception;

public class RegistrationException extends CustomeException{
    public RegistrationException(String message) {
        super(message);
    }
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
