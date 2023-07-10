package co.ke.unicite.Entities.RoomManagement;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="grades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grades implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="score")
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "fk_assignment")
    private Assignments assignments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "fk_user")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "fk_course")
    private CourseGroups courseGroups;

    public Grades(@NotNull int score) {
        this.score = score;
    }
}
