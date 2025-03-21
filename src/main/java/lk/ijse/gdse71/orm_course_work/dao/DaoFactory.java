package lk.ijse.gdse71.orm_course_work.dao;

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
       USER
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperDao>T getDAO(DAOType type){

        switch (type){
            case USER:
                return (T) new UserDaoImpl();
            default:
                return null;
        }
    }
}
