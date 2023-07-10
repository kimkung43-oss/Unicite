package co.ke.unicite.Entities;

import co.ke.unicite.Entities.RoomManagement.Courses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "announcements")
@AllArgsConstructor
@NoArgsConstructor

public class Announcements implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcement_id;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String description;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private LocalDate createdDate;

    @Column(name="scheduled_date")
    private LocalDate scheduledDate;

    @NonNull
    @NotNull
    @Column(name="status")
    private Boolean isActive;


    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Courses course;

    public Announcements(String title, String description, LocalDate createdDate, @NonNull @NotNull Boolean isActive) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }
}
