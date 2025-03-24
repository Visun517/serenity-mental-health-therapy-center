package lk.ijse.gdse71.orm_course_work.dao;

import lk.ijse.gdse71.orm_course_work.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public static DaoFactory getInstance(){
        if(daoFactory==null){
            daoFactory=new DaoFactory();
        }
        return daoFactory;
    }
    public enum DAOType{
       USER,THERAPISTS,PROGRAM,THERAPISTS_PROGRAM,PATIENT,PATIENT_PROGRAM,QUERY
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperDao>T getDAO(DAOType type){

        switch (type){
            case USER:
                return (T) new UserDaoImpl();
            case THERAPISTS:
                return (T) new TheraphistsDaoImpl();
            case PROGRAM:
                return (T) new ProgramsDaoImpl();
            case THERAPISTS_PROGRAM:
                return (T) new TherapstsProgramsDaoImpl();
            case PATIENT:
                return (T) new PatientDaoImpl();
            case PATIENT_PROGRAM:
                return (T) new PatientProgramsDaoImpl();
            case QUERY:
                return (T) new QueryDaoImpl();
            default:
                return null;
        }
    }
}
