package lk.ijse.gdse71.orm_course_work.dao;

import lk.ijse.gdse71.orm_course_work.dao.custom.impl.ProgramsDaoImpl;
import lk.ijse.gdse71.orm_course_work.dao.custom.impl.TheraphistsDaoImpl;
import lk.ijse.gdse71.orm_course_work.dao.custom.impl.TherapstsProgramsDaoImpl;
import lk.ijse.gdse71.orm_course_work.dao.custom.impl.UserDaoImpl;

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
       USER,THERAPISTS,PROGRAM,THERAPISTS_PROGRAM
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
            default:
                return null;
        }
    }
}
