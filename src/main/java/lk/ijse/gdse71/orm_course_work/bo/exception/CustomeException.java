package lk.ijse.gdse71.orm_course_work.bo.exception;

public class CustomeException extends Exception {
    public  CustomeException(String message) {
        super(message);
    }

    public  CustomeException(String message, Throwable cause) {
        super(message, cause);
    }
}
