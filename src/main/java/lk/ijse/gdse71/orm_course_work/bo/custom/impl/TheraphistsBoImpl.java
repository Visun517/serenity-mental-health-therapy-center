package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.TheraphistsBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.QueryDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.TheraphistsDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.impl.TherapstsProgramsDaoImpl;
import lk.ijse.gdse71.orm_course_work.dto.TherapistDto;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TheraphistsBoImpl implements TheraphistsBo {

    private final TheraphistsDao theraphistsDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.THERAPISTS);
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);
    private final TherapstsProgramsDaoImpl therapstsProgramsDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.THERAPISTS_PROGRAM);
    private final QueryDao queryDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.QUERY);
    @Override
    public String getNextId() throws SQLException {
        String id = theraphistsDao.getNextId();

        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }
        return "T001";
    }

    @Override
    public List<TherapistDto> getAll() throws SQLException {
        List<Theraphist> therapists = theraphistsDao.getAll();
        List<TherapistDto> therapistDtos = new ArrayList<>();

        for (Theraphist therapist : therapists) {
            TherapistDto therapistDto = new TherapistDto();
            therapistDto.setTheraphists_id(therapist.getTheraphists_id());
            therapistDto.setContact(therapist.getContact());
            therapistDto.setName(therapist.getName());
            therapistDto.setEmail(therapist.getEmail());

            therapistDtos.add(therapistDto);
        }
        return therapistDtos;
    }

    @Override
    public boolean saveTherapist(TherapistDto therapistDto,List<String> programNames) throws SQLException {
        Theraphist theraphist = new Theraphist();
        theraphist.setTheraphists_id(therapistDto.getTheraphists_id());
        theraphist.setName(therapistDto.getName());
        theraphist.setContact(therapistDto.getContact());
        theraphist.setEmail(therapistDto.getEmail());

        List<TheraphyProgram> theraphyPrograms = new ArrayList<>();
        for (String programName : programNames) {
            TheraphyProgram theraphyProgram = progrmasDao.getNameFromProgram(programName);
            theraphyPrograms.add(theraphyProgram);
        }

        theraphist.getTheraphyPrograms().addAll(theraphyPrograms);

        return theraphistsDao.save(theraphist);

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return theraphistsDao.delete(id);
    }

    @Override
    public boolean update(TherapistDto therapistDto,List<String> programNames) throws SQLException {
        Theraphist theraphist = new Theraphist();
        theraphist.setTheraphists_id(therapistDto.getTheraphists_id());
        theraphist.setName(therapistDto.getName());
        theraphist.setContact(therapistDto.getContact());
        theraphist.setEmail(therapistDto.getEmail());

        List<TheraphyProgram> theraphyPrograms = new ArrayList<>();
        for (String programName : programNames) {
            TheraphyProgram theraphyProgram = progrmasDao.getNameFromProgram(programName);
            theraphyPrograms.add(theraphyProgram);
        }

        theraphist.getTheraphyPrograms().addAll(theraphyPrograms);

        return theraphistsDao.update(theraphist);
    }

    @Override
    public List<TheraphyProgram> getAssigningPrograms(String therapistId) throws SQLException {
        List<String> assigningPrograms = therapstsProgramsDao.getAssigningPrograms(therapistId);
        List<TheraphyProgram> assignPrograms1 = new ArrayList<>();

        for (String programId : assigningPrograms) {
            TheraphyProgram theraphyProgram = progrmasDao.getIdFromProgram(programId);
            assignPrograms1.add(theraphyProgram);
        }
        return assignPrograms1;
    }

    @Override
    public List<TherapistDto> getAvailableTherapist(String programId, Time time, Date date) {
        List<Theraphist> therapists = queryDao.getAvailableTherapist(programId, time, date);

        List<TherapistDto> therapistDtos = new ArrayList<>();
        for (Theraphist therapist : therapists) {
            TherapistDto therapistDto = new TherapistDto();
            therapistDto.setTheraphists_id(therapist.getTheraphists_id());
            therapistDto.setName(therapist.getName());
            therapistDto.setContact(therapist.getContact());
            therapistDto.setEmail(therapist.getEmail());
            therapistDtos.add(therapistDto);
        }
        return therapistDtos;
    }

    @Override
    public TherapistDto getTheraphist(String therapistId) {
        Theraphist theraphist = theraphistsDao.getTherapist(therapistId);
        TherapistDto therapistDto = new TherapistDto();
        therapistDto.setTheraphists_id(theraphist.getTheraphists_id());
        therapistDto.setName(theraphist.getName());
        therapistDto.setContact(theraphist.getContact());
        therapistDto.setEmail(theraphist.getEmail());
        return therapistDto;
    }

}
