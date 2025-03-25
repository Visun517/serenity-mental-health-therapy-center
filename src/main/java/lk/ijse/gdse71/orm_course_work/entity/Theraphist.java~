package lk.ijse.gdse71.orm_course_work.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Theraphist {
    @Id
    private String theraphists_id;
    private String name;
    private String email;
    private String contact;

    @OneToMany(mappedBy = "theraphist" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<TheraphySession> theraphySessions;
    @ManyToMany
    @JoinTable(
            name = "theraphists_programs",
            joinColumns = @JoinColumn(name = "theraphists_id"),
            inverseJoinColumns = @JoinColumn(name = "theraphy_pro_id")
    )
    private List<TheraphyProgram> theraphyPrograms = new ArrayList<>();



}
