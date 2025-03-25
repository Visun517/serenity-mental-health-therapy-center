package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.SessionDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.TheraphistsDao;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionBoImpl implements SessionBo {
    private final SessionDao sessionDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.SESSIONS);
    private final PatientDao patientDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PATIENT);
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);
    private final TheraphistsDao theraphistsDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.THERAPISTS);
    @Override
    public String getNextId() throws SQLException {
        String id = sessionDao.getNextId();
        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    @Override
    public List<SessionDto> getAll() throws SQLException {
        List<TheraphySession> all = sessionDao.getAll();
        List<SessionDto> sessionDtos = new ArrayList<>();

        for (TheraphySession session : all) {
            SessionDto sessionDto = new SessionDto();
            sessionDto.setSession_id(session.getSession_id());
            sessionDto.setDate(session.getDate());
            sessionDto.setTime(session.getTime());
            sessionDto.setStatus(session.getStatus());
            sessionDto.setPatient_id(session.getPatient().getPatient_id());
            sessionDto.setTherapist_id(session.getTheraphist().getTheraphists_id());
            sessionDto.setProgram_id(session.getTheraphyProgram().getTheraphy_pro_id());

            sessionDtos.add(sessionDto);
        }
    return sessionDtos;
    }

    @Override
    public boolean book(SessionDto sessionDto) throws SQLException {
        TheraphySession theraphySession = new TheraphySession();
        theraphySession.setSession_id(sessionDto.getSession_id());
        theraphySession.setPatient(patientDao.getPatient(sessionDto.getPatient_id()));
        theraphySession.setTheraphyProgram(progrmasDao.getIdFromProgram(sessionDto.getProgram_id()));
        theraphySession.setTheraphist(theraphistsDao.getTherapist(sessionDto.getTherapist_id()));
        theraphySession.setDate(sessionDto.getDate());
        theraphySession.setTime(sessionDto.getTime());
        theraphySession.setStatus(sessionDto.getStatus());

        return sessionDao.save(theraphySession);
    }

    @Override
    public boolean cancel(SessionDto sessionDto) throws SQLException {
        TheraphySession theraphySession = new TheraphySession();
        theraphySession.setSession_id(sessionDto.getSession_id());
        theraphySession.setPatient(patientDao.getPatient(sessionDto.getPatient_id()));
        theraphySession.setTheraphyProgram(progrmasDao.getIdFromProgram(sessionDto.getProgram_id()));
        theraphySession.setTheraphist(theraphistsDao.getTherapist(sessionDto.getTherapist_id()));
        theraphySession.setDate(sessionDto.getDate());
        theraphySession.setTime(sessionDto.getTime());
        theraphySession.setStatus(sessionDto.getStatus());

        return sessionDao.cancel(theraphySession);

    }

    @Override
    public boolean reschedule(SessionDto sessionDto) throws SQLException {
        TheraphySession theraphySession = new TheraphySession();
        theraphySession.setSession_id(sessionDto.getSession_id());
        theraphySession.setPatient(patientDao.getPatient(sessionDto.getPatient_id()));
        theraphySession.setTheraphyProgram(progrmasDao.getIdFromProgram(sessionDto.getProgram_id()));
        theraphySession.setTheraphist(theraphistsDao.getTherapist(sessionDto.getTherapist_id()));
        theraphySession.setDate(sessionDto.getDate());
        theraphySession.setTime(sessionDto.getTime());
        theraphySession.setStatus(sessionDto.getStatus());

        return sessionDao.reschedule(theraphySession);
    }

    @Override
    public List<SessionDto> getTherapistSchedule(String therapistId) {
        List<TheraphySession> therapistSchedule = sessionDao.getTherapistSchedule(therapistId);

        List<SessionDto> sessionDtos = new ArrayList<>();
        for (TheraphySession session : therapistSchedule){
            SessionDto sessionDto = new SessionDto();
            sessionDto.setSession_id(session.getSession_id());
            sessionDto.setDate(session.getDate());
            sessionDto.setTime(session.getTime());
            sessionDto.setStatus(session.getStatus());
            sessionDto.setPatient_id(session.getPatient().getPatient_id());
            sessionDto.setTherapist_id(session.getTheraphist().getTheraphists_id());
            sessionDto.setProgram_id(session.getTheraphyProgram().getTheraphy_pro_id());

            sessionDtos.add(sessionDto);
        }
       return sessionDtos;
    }
}
