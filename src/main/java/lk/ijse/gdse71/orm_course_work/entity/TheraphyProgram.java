package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tharaphy_program")
public class TheraphyProgram {
    @Id
    private String theraphy_pro_id;
    private String name;
    private String duration;
    private double fee;
    private String description;

    @OneToMany(mappedBy = "theraphyProgram" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<PatientProgramsDetails> patientProgramsDetails;
    @OneToMany(mappedBy = "theraphyProgram" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Payment> payments;
    @OneToMany(mappedBy = "theraphyProgram" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<TheraphySession> theraphySessions;
    @ManyToMany(mappedBy = "theraphyPrograms")
    private List<Theraphist> theraphists;
}
