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
@Table(name = "notes")
@AllArgsConstructor
@NoArgsConstructor
public class Notes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long note_id;

    @Column(length = 100)
    private String title;

    @Column(length = 255)
    private String content;

    @NotNull
    @NonNull
//    @Temporal(TemporalType.DATE)
    @Column(name="created_date")
    private LocalDate createdDate;

//    @Temporal(TemporalType.DATE)
    @Column(name="modified_date")
    private LocalDate modifiedDate;


    @JsonIgnore
    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    public Notes(String title, String content, @NotNull @NonNull LocalDate createdDate, LocalDate modifiedDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
