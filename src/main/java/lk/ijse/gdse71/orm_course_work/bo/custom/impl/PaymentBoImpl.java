package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.PaymentBo;
import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.PaymentDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.SessionDao;
import lk.ijse.gdse71.orm_course_work.dto.PaymentDto;
import lk.ijse.gdse71.orm_course_work.entity.PatientProgramsDetails;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import lk.ijse.gdse71.orm_course_work.entity.Payment;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBoImpl implements PaymentBo {
    private final PaymentDao paymentDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PAYMENT);
    private final PatientProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PATIENT_PROGRAM);
    private final SessionDao sessionDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.SESSIONS);
    private final PatientProgrmasDao patientProgrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PATIENT_PROGRAM);

    @Override
    public String getNextId() throws SQLException {
        String id = paymentDao.getNextId();

        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("PA%03d", newIdIndex);
        }
        return "PA001";
    }

    @Override
    public List<PaymentDto> getAll() throws SQLException {
        List<Payment> all = paymentDao.getAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();

        for (Payment payment : all) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPayment_id(payment.getPayment_id());
            paymentDto.setDate(payment.getDate());
            paymentDto.setStatus(payment.getStatus());
            paymentDto.setAmount(payment.getAmount());
            paymentDto.setPatientId(payment.getPatient().getPatient_id());
            paymentDto.setSessionId(payment.getSession().getSession_id());
            paymentDto.setTheraphyProgramId(payment.getTheraphyProgram().getTheraphy_pro_id());

            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    @Override
    public boolean save(PaymentDto dto) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setPayment_id(dto.getPayment_id());
        payment.setDate(dto.getDate());
        payment.setStatus(dto.getStatus());
        payment.setAmount(dto.getAmount());

        TheraphySession theraphySession = sessionDao.getSession(dto.getSessionId());
        if (theraphySession != null) {
            payment.setSession(theraphySession);
            payment.setTheraphyProgram(theraphySession.getTheraphyProgram());
            payment.setPatient(theraphySession.getPatient());
        }

        PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
        patinetProgramsDetailsIds.setPatient_id(dto.getPatientId());
        patinetProgramsDetailsIds.setTheraphy_pro_id(dto.getTheraphyProgramId());

        try {
            boolean isSaved = paymentDao.sevaPayment(payment, session);
            if (isSaved) {
                patientProgrmasDao.reducePayment(patinetProgramsDetailsIds, payment.getAmount(), session);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }


    }

    @Override
    public boolean update(PaymentDto dto) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setPayment_id(dto.getPayment_id());
        payment.setDate(dto.getDate());
        payment.setStatus(dto.getStatus());
        payment.setAmount(dto.getAmount());

        TheraphySession theraphySession = sessionDao.getSession(dto.getSessionId());
        if (theraphySession != null) {
            payment.setSession(theraphySession);
            payment.setTheraphyProgram(theraphySession.getTheraphyProgram());
            payment.setPatient(theraphySession.getPatient());
        }

        PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
        patinetProgramsDetailsIds.setPatient_id(dto.getPatientId());
        patinetProgramsDetailsIds.setTheraphy_pro_id(dto.getTheraphyProgramId());

        try {
            boolean isUpdate = paymentDao.sevaPayment(payment, session);
            if (isUpdate) {
                Payment secondPayment = paymentDao.getPayment(payment.getPayment_id());

                double updatedAmount = secondPayment.getAmount() - payment.getAmount();

                if (secondPayment.getAmount() > payment.getAmount()) {
                    patientProgrmasDao.addUpPayment(patinetProgramsDetailsIds, payment.getAmount(), session);
                } else {
                    patientProgrmasDao.reducePayment(patinetProgramsDetailsIds, payment.getAmount(), session);
                }

            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            boolean isDeleted = paymentDao.delete(id,session);

            Payment payment = paymentDao.getPayment(id);

            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(payment.getPatient().getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(payment.getTheraphyProgram().getTheraphy_pro_id());

            if (isDeleted) {
                patientProgrmasDao.addUpPayment(patinetProgramsDetailsIds, payment.getAmount(), session);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public double getDuePayment(PatinetProgramsDetailsIds patinetProgramsDetailsIds) {
        PatientProgramsDetails patientProgramsDetailsDto = progrmasDao.getDuePayment(patinetProgramsDetailsIds);
        return patientProgramsDetailsDto.getProgramAmount();
    }

    @Override
    public PaymentDto getPayment(String paymentId) {
        Payment payment = paymentDao.getPayment(paymentId);
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment_id(payment.getPayment_id());
        paymentDto.setDate(payment.getDate());
        paymentDto.setStatus(payment.getStatus());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPatientId(payment.getPatient().getPatient_id());
        paymentDto.setSessionId(payment.getSession().getSession_id());
        paymentDto.setTheraphyProgramId(payment.getTheraphyProgram().getTheraphy_pro_id());
        return paymentDto;
    }

}
