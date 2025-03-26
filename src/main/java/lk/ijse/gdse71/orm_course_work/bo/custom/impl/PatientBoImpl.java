package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.PatientProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dao.custom.QueryDao;
import lk.ijse.gdse71.orm_course_work.dto.FilterDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.entity.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBoImpl implements PatientBo {

    private final PatientDao patientDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PATIENT);
    private final PatientProgrmasDao patientProgrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PATIENT_PROGRAM);
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);
    private final QueryDao queryDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.QUERY);

    @Override
    public String getNextId() throws SQLException {
        String id = patientDao.getNextId();

        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public List<PatientDto> getAll() throws SQLException {
        List<Patient> all = patientDao.getAll();
        List<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : all) {
            PatientDto patientDto = new PatientDto();
            patientDto.setPatient_id(patient.getPatient_id());
            patientDto.setName(patient.getName());
            patientDto.setContact(patient.getContact());
            patientDto.setEmail(patient.getEmail());
            patientDto.setDate(patient.getDate());
            patientDto.setMedical_history(patient.getMedical_history());
            patientDtos.add(patientDto);
        }
        return patientDtos;
    }

    @Override
    public List<TheraphyProgram> getPatientPrograms(String patientId) throws SQLException {
        List<String> patientPrograms = queryDao.getPatientPrograms(patientId);
        List<TheraphyProgram> assignPrograms1 = new ArrayList<>();

        for (String programId : patientPrograms) {
            TheraphyProgram theraphyProgram = progrmasDao.getIdFromProgram(programId);
            assignPrograms1.add(theraphyProgram);
        }
        return assignPrograms1;
    }

    @Override
    public String getPatinetStatus(PatinetProgramsDetailsIds patientId) {
        return patientProgrmasDao.getPatientStatus(patientId);
    }

    @Override
    public boolean patinetSave(PatientDto patientDto, String status, ObservableList<String> programNames) throws SQLException {
        Patient patient = new Patient();
        patient.setPatient_id(patientDto.getPatient_id());
        patient.setName(patientDto.getName());
        patient.setContact(patientDto.getContact());
        patient.setDate(patientDto.getDate());
        patient.setMedical_history(patientDto.getMedical_history());
        patient.setEmail(patientDto.getEmail());

        List<PatientProgramsDetails> patientProgramsDetails1 = new ArrayList<>();

        for (String name : programNames) {
            PatientProgramsDetails patientProgramsDetails = new PatientProgramsDetails();
            patientProgramsDetails.setStatus(status);

            TheraphyProgram theraphyProgram = progrmasDao.getNameFromProgram(name);
            patientProgramsDetails.setTheraphyProgram(theraphyProgram);

            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(patientDto.getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(theraphyProgram.getTheraphy_pro_id());
            patientProgramsDetails.setPatient(patient);

            patientProgramsDetails.setIds(patinetProgramsDetailsIds);

            patientProgramsDetails1.add(patientProgramsDetails);

        }
        patient.setPatientProgramsDetails(patientProgramsDetails1);

        return patientDao.save(patient);


    }

    @Override
    public boolean update(PatientDto patientDto, String status, ObservableList<String> programNames) throws SQLException {
        Patient patient = new Patient();
        patient.setPatient_id(patientDto.getPatient_id());
        patient.setName(patientDto.getName());
        patient.setContact(patientDto.getContact());
        patient.setDate(patientDto.getDate());
        patient.setMedical_history(patientDto.getMedical_history());
        patient.setEmail(patientDto.getEmail());

        List<PatientProgramsDetails> patientProgramsDetails1 = new ArrayList<>();

        for (String name : programNames) {
            PatientProgramsDetails patientProgramsDetails = new PatientProgramsDetails();
            patientProgramsDetails.setStatus(status);

            TheraphyProgram theraphyProgram = progrmasDao.getNameFromProgram(name);
            patientProgramsDetails.setTheraphyProgram(theraphyProgram);

            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(patientDto.getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(theraphyProgram.getTheraphy_pro_id());
            patientProgramsDetails.setPatient(patient);

            patientProgramsDetails.setIds(patinetProgramsDetailsIds);

            patientProgramsDetails1.add(patientProgramsDetails);

        }
        patient.setPatientProgramsDetails(patientProgramsDetails1);

        return patientDao.update(patient);


    }

    @Override
    public boolean delete(String id) throws SQLException {
       return patientDao.delete(id);
    }

    @Override
    public List<PatientDto> getFullEnrolledPatients() {
        queryDao.getFullEnrolledPatients();
        return null;
    }

    @Override
    public List<FilterDto> filterByStatus(String status) {
        return queryDao.filterByStatus(status);
    }

    @Override
    public List<FilterDto> filterByProgramId(String programId) {
        return queryDao.filterByProgramId(programId);
    }
}
