package lk.ijse.gdse71.orm_course_work.userDetails;

import lk.ijse.gdse71.orm_course_work.dto.UserDto;

public class UserDetails {
    private static UserDetails instance;
    private UserDto loggedInUser;

    private UserDetails() {
    }

    public static UserDetails getInstance() {
        if (instance == null) {
            instance = new UserDetails();
        }
        return instance;
    }

    public void setLoggedInUser(UserDto user) {
        this.loggedInUser = user;
    }

    public UserDto getLoggedInUser() {
        return loggedInUser;
    }

    public void clearSession() {
        this.loggedInUser = null;
    }
}