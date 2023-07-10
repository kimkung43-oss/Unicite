package co.ke.unicite.Entities.TaskManagement;

import co.ke.unicite.Entities.UserManagement.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Tasks implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String description;

//    @Temporal(TemporalType.DATE)
    @Column(name="created_date")
    private LocalDate createdDate;

    @NonNull
    @NotNull
    @Column(name="status_completed")
    private Boolean isCompleted;


    @JsonIgnore
    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    public Tasks(String title, String description, LocalDate createdDate, @NonNull @NotNull Boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.isCompleted = isCompleted;
    }
}
