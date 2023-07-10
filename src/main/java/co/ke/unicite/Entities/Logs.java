package co.ke.unicite.Entities;

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
@Table(name="logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logs implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @NonNull
    @NotNull
    @Column(length = 255)
    private String action;

    @NonNull
    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    public Logs(@NonNull @NotNull String action, @NonNull @NotNull LocalDateTime timestamp) {
        this.action = action;
        this.timestamp = timestamp;
    }
}


