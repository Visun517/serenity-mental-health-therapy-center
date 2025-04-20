package lk.ijse.gdse71.orm_course_work.dao.custom;

import lk.ijse.gdse71.orm_course_work.dao.CrudDao;
import lk.ijse.gdse71.orm_course_work.entity.Payment;
import org.hibernate.Session;

public interface PaymentDao extends CrudDao<Payment> {
    boolean sevaPayment(Payment payment, Session session);
    Payment getPayment(String paymentId);
    boolean delete(String id, Session session);
}
