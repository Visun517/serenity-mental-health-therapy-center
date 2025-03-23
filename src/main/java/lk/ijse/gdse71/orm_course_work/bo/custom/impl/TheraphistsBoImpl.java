package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.TheraphistsBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.TheraphistsDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.impl.TherapstsProgramsDaoImpl;
import lk.ijse.gdse71.orm_course_work.dto.TherapistDto;
import lk.ijse.gdse71.orm_course_work.entity.Theraphist;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheraphistsBoImpl implements TheraphistsBo {

    private final TheraphistsDao theraphistsDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.THERAPISTS);
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);
    private final TherapstsProgramsDaoImpl therapstsProgramsDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.THERAPISTS_PROGRAM);
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

}
