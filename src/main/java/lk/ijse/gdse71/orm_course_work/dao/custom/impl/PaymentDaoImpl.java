package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.PaymentDao;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public String getNextId() throws SQLException {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("select p.payment_id from Payment p order by p.payment_id desc", String.class).setMaxResults(1).uniqueResult();
        return lastId;
    }
    @Override
    public boolean sevaPayment(Payment payment, Session session) {
        try {
            session.merge(payment);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Payment getPayment(String paymentId) {
        Session session = factoryConfiguration.getSession();
        Payment payment = session.get(Payment.class, paymentId);
        session.close();
        return payment;
    }

    @Override
    public boolean delete(String id, Session session) {
        try{
            Payment payment = session.get(Payment.class, id);
            if (payment != null){
                session.remove(payment);
                return true;
            }else{
                System.out.println("User is null");
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        Session session = factoryConfiguration.getSession();
        List<Payment> payments = session.createQuery("from Payment ", Payment.class).list();
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        paymentArrayList.addAll(payments);

        return paymentArrayList;
    }

    @Override
    public boolean save(Payment dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Payment dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }


}
