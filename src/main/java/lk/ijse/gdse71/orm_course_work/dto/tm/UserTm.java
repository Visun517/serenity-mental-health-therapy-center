package lk.ijse.gdse71.orm_course_work.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTm {
    private String user_id;
    private String username;
    private String password;
    private String role;
}
