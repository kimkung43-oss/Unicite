package co.ke.unicite.Entities.RoomManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="assignments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignments implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NonNull
    @Column(name = "title",length = 100)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name="status")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "fk_course")
    private Courses course;

    // Mapping relationship with Group
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_group")
    private CourseGroups group;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AssignmentSubmission> assignmentSubmission= new HashSet<>();

    public Assignments(@NotNull String title, String description, LocalDateTime deadline, boolean isActive) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isActive = isActive;
    }
}
