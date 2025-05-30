package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.SchedulingConfiltException;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.*;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
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
    private final QueryDao queryDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.QUERY);

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
    public boolean book(SessionDto sessionDto) throws SQLException, SchedulingConfiltException {

        if (sessionDto.getSession_id().isEmpty() && sessionDto.getPatient_id().isEmpty() && sessionDto.getProgram_id().isEmpty() && sessionDto.getTherapist_id().isEmpty() && sessionDto.getDate()== null && sessionDto.getTime()==null && sessionDto.getStatus().isEmpty()) {
            throw new SchedulingConfiltException("missing fields");
        }

        if (sessionDao.getSession(sessionDto.getSession_id()) != null) {
            throw new SchedulingConfiltException("session already exists");
        }
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
    public boolean cancel(SessionDto sessionDto) throws SQLException, SchedulingConfiltException {
        if (sessionDto.getSession_id().isEmpty() && sessionDto.getPatient_id().isEmpty() && sessionDto.getProgram_id().isEmpty() && sessionDto.getTherapist_id().isEmpty() && sessionDto.getDate()== null && sessionDto.getTime()==null && sessionDto.getStatus().isEmpty()) {
            throw new SchedulingConfiltException("missing fields");
        }

        if (sessionDao.getSession(sessionDto.getSession_id()) != null) {
            throw new SchedulingConfiltException("session already exists");
        }

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
    public boolean reschedule(SessionDto sessionDto) throws SQLException, SchedulingConfiltException {
        if (sessionDto.getSession_id().isEmpty() && sessionDto.getPatient_id().isEmpty() && sessionDto.getProgram_id().isEmpty() && sessionDto.getTherapist_id().isEmpty() && sessionDto.getDate()== null && sessionDto.getTime()==null && sessionDto.getStatus().isEmpty()) {
            throw new SchedulingConfiltException("missing fields");
        }

        if (sessionDao.getSession(sessionDto.getSession_id()) != null) {
            throw new SchedulingConfiltException("session already exists");
        }
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

    @Override
    public SessionDto getSession(String sessionId) throws SQLException {
        TheraphySession theraphySession = sessionDao.getSession(sessionId);
        SessionDto sessionDto = null;
        if (theraphySession != null) {
            sessionDto = new SessionDto();
            sessionDto.setSession_id(theraphySession.getSession_id());
            sessionDto.setProgram_id(theraphySession.getTheraphyProgram().getTheraphy_pro_id());
            sessionDto.setPatient_id(theraphySession.getPatient().getPatient_id());
            sessionDto.setDate(theraphySession.getDate());
            sessionDto.setTime(theraphySession.getTime());
            sessionDto.setStatus(theraphySession.getStatus());
            sessionDto.setTherapist_id(theraphySession.getTheraphist().getTheraphists_id());
        }
        return sessionDto;
    }

    @Override
    public List<SessionStaticsDto> getSessionStatistics() {
        List<SessionStaticsDto> sessionStaticsDtos = queryDao.getSessionStatistics();
        return sessionStaticsDtos;
    }

    @Override
    public String getPatinetProgramSession(String patientId, String programId) throws SQLException {
        String sessionId = sessionDao.getSessionId(patientId, programId);
        System.out.println(sessionId);
        return sessionId;

    }
}
