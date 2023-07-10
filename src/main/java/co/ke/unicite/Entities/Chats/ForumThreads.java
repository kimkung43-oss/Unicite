package co.ke.unicite.Entities.Chats;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="forum_threads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumThreads implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thread_id;

    @NonNull
    @NotNull
    @Column(length = 100)
    private String threadTitle;

    @NonNull
    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @NonNull
    @NotNull
    @Column(name="status")
    private Boolean isActive;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    @OneToMany(mappedBy= "forumThread", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ForumReply> forumReplies = new HashSet<>();

    public ForumThreads(@NonNull @NotNull String threadTitle, @NonNull @NotNull LocalDateTime timestamp, @NonNull @NotNull Boolean isActive) {
        this.threadTitle = threadTitle;
        this.timestamp = timestamp;
        this.isActive = isActive;
    }
}
