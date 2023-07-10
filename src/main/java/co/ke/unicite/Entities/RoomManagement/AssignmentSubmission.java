package co.ke.unicite.Entities.RoomManagement;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="assignment_submissions")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssignmentSubmission implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

//    @NonNull
//    @NotNull
    @Column
    private String filepath;

    // Mapping relationship with Assignment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_assignment")
    private Assignments assignment;

    // Mapping relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_group")
    private CourseGroups courseGroups;

    public AssignmentSubmission(@NonNull @NotNull LocalDateTime submissionDate, String filepath, Assignments assignment) {
        this.submissionDate = submissionDate;
        this.filepath = filepath;
        this.assignment = assignment;
    }
}
