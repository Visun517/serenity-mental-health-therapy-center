package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    private String patient_id;
    private String name;
    private String contact;
    private String email;
    private Date date;
    private String medical_history;

    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Payment> payment;
    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<TheraphySession> sessions;
    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<PatientProgramsDetails> patientProgramsDetails;
}