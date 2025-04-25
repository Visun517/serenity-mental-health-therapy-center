package lk.ijse.gdse71.orm_course_work.bo.exception;

public class PaymentException extends CustomeException{
    public PaymentException(String message) {
        super(message);
    }
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}

