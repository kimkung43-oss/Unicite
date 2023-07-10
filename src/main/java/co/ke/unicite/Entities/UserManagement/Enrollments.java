package co.ke.unicite.Entities.UserManagement;

import co.ke.unicite.Entities.RoomManagement.Courses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

//Covers the courses a user is registered in
@Entity
@Table(name="Enrollments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="enrollment_id")
    private Long enrollmentId;

    @Column(name="status")
    private Boolean isActive;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDate enrolledDate;

    @NotNull
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    @NotNull
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_course")
    private Courses course;

    public Enrollments(Boolean isActive, LocalDate enrolledDate) {
        this.isActive = isActive;
        this.enrolledDate = enrolledDate;
    }
}
