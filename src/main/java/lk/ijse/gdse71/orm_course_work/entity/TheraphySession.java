package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TheraphySession {
    @Id
    private String session_id;
    private Date date;
    private Time time;

    @ManyToOne
    private Patient patient;
    @OneToMany(mappedBy = "session" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Payment> payment;
    @ManyToOne
    private TheraphyProgram theraphyProgram;
    @ManyToOne
    private Theraphist theraphist;


}
