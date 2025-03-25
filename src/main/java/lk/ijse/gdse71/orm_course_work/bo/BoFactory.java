package lk.ijse.gdse71.orm_course_work.bo;
import lk.ijse.gdse71.orm_course_work.bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getInstance() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public  enum BOType{
        USER,PASSWORD,THERAPIST,PROGRAMS,PATIENT,SESSION
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperBo> T getBo(BOType type){

        switch (type){
            case USER:
                return (T) new UserBoImpl();
            case PASSWORD:
                return (T) new PasswordEncryptBoImpl();
            case THERAPIST:
                return (T) new TheraphistsBoImpl();
            case PROGRAMS:
                return (T) new ProgrmasBoImpl();
            case PATIENT:
                return (T) new PatientBoImpl();
            case SESSION:
                return (T) new SessionBoImpl();
            default:
                return null;
        }
    }
}
