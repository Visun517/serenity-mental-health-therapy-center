package lk.ijse.gdse71.orm_course_work.bo;
import lk.ijse.gdse71.orm_course_work.bo.custom.impl.PasswordEncryptBoImpl;
import lk.ijse.gdse71.orm_course_work.bo.custom.impl.UserBoImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getInstance() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public  enum BOType{
        USER,PASSWORD
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBo> T getBo(BOType type){

        switch (type){
            case USER:
                return (T) new UserBoImpl();
            case PASSWORD:
                return (T) new PasswordEncryptBoImpl();
            default:
                return null;
        }
    }
}
