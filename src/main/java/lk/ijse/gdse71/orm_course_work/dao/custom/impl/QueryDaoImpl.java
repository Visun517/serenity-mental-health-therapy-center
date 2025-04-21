package lk.ijse.gdse71.orm_course_work.dao.custom.impl;

import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import lk.ijse.gdse71.orm_course_work.dao.custom.QueryDao;
import lk.ijse.gdse71.orm_course_work.dto.FilterDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto;
import lk.ijse.gdse71.orm_course_work.dto.TherapistReportDto;
import lk.ijse.gdse71.orm_course_work.entity.Patient;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class QueryDaoImpl implements QueryDao {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<String> getPatientPrograms(String patientId) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT pp.theraphyProgram.theraphy_pro_id FROM Patient p JOIN p.patientProgramsDetails pp  WHERE p.patient_id = :patientId",
                    String.class
            );
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Theraphist> getAvailableTherapist(String programId, Time time, Date date) {
        Session session = factoryConfiguration.getSession();

        try {
            Query<Theraphist> query = session.createQuery("SELECT t FROM Theraphist t JOIN t.theraphyPrograms tp WHERE tp.theraphy_pro_id = :programId", Theraphist.class);
            query.setParameter("programId", programId);
            List<Theraphist> therapists = query.getResultList();

            List<Theraphist> availableTheraphistList = new ArrayList<>();
            for (Theraphist theraphist : therapists) {
                Query<TheraphySession> query1 = session.createQuery("FROM TheraphySession ts WHERE ts.theraphist = :therapist AND ts.date = :date AND ts.time = :time", TheraphySession.class);
                query1.setParameter("therapist", theraphist);
                query1.setParameter("date", date);
                query1.setParameter("time", time);
                List<TheraphySession> sessions = query1.getResultList();

                if (sessions.isEmpty()) {
                    availableTheraphistList.add(theraphist);
                }
            }
            return availableTheraphistList;

        } finally {
            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public List<FilterDto> filterByStatus(String status) {
        Session session = factoryConfiguration.getSession();

        try {
            Query<Object[]> query = session.createQuery("SELECT p.patient_id, p.name, p.contact, p.email, s.session_id, s.date, s.theraphyProgram.theraphy_pro_id " +
                    "FROM Patient p JOIN p.sessions s WHERE s.status = :status", Object[].class);
            query.setParameter("status", status);

            List<Object[]> list = query.getResultList();

            List<FilterDto> filterDtos = new ArrayList<>();

            for (Object[] objects : list) {
                FilterDto filterDto = new FilterDto();
                filterDto.setPatient_id((String) objects[0]);
                filterDto.setName((String) objects[1]);
                filterDto.setContact((String) objects[2]);
                filterDto.setEmail((String) objects[3]);
                filterDto.setSession_id((String) objects[4]);
                filterDto.setDate((Date) objects[5]);
                filterDto.setProgram_id((String) objects[6]);
                filterDtos.add(filterDto);
            }
            return filterDtos;
        } finally {
            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public List<FilterDto> filterByProgramId(String programId) {
        Session session = factoryConfiguration.getSession();

        try {
            Query<Object[]> query = session.createQuery("SELECT p.patient_id, p.name, p.contact, p.email, s.session_id, s.date, s.theraphyProgram.theraphy_pro_id " +
                    "FROM Patient p JOIN p.sessions s WHERE s.theraphyProgram.theraphy_pro_id = :programId", Object[].class);
            query.setParameter("programId", programId);

            List<Object[]> list = query.getResultList();

            List<FilterDto> filterDtos = new ArrayList<>();

            for (Object[] objects : list) {
                FilterDto filterDto = new FilterDto();
                filterDto.setPatient_id((String) objects[0]);
                filterDto.setName((String) objects[1]);
                filterDto.setContact((String) objects[2]);
                filterDto.setEmail((String) objects[3]);
                filterDto.setSession_id((String) objects[4]);
                filterDto.setDate((Date) objects[5]);
                filterDto.setProgram_id((String) objects[6]);
                filterDtos.add(filterDto);
            }
            return filterDtos;
        } finally {
            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public List<TherapistReportDto> getTherapistPerformance() {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "SELECT new lk.ijse.gdse71.orm_course_work.dto.TherapistReportDto(t.theraphists_id, t.name, COUNT(s), " +
                    "(SUM(CASE WHEN s.status = 'COMPLETED' THEN 1 ELSE 0 END) * 100.0 / COUNT(s)), " +
                    "COUNT(s)) " +
                    "FROM Theraphist t LEFT JOIN t.theraphySessions s GROUP BY t.theraphists_id, t.name";

            Query query = session.createQuery(hql);
            return query.list();

        } finally {
            session.close();
        }
    }

    @Override
    public List<SessionStaticsDto> getSessionStatistics() {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "SELECT new lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto(p.name, COUNT(s), " +
                    "SUM(CASE WHEN s.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
                    "SUM(CASE WHEN s.status = 'PENDING' THEN 1 ELSE 0 END)) " +
                    "FROM TheraphyProgram p JOIN p.theraphySessions s GROUP BY p.name";

            Query query = session.createQuery(hql);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Patient> getFullEnrolledPatients() {
        Session session = factoryConfiguration.getSession();

        try {
            return session.createQuery("SELECT p FROM Patient p " + "JOIN p.patientProgramsDetails ps " + "GROUP BY p " + "HAVING COUNT(DISTINCT ps.theraphyProgram) = (SELECT COUNT(*) FROM TheraphyProgram)", Patient.class).getResultList();
        } finally {
            if (session != null) {
                session.close();
            }

        }
    }

}
