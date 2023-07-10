package co.ke.unicite.Entities.Chats;

import java.io.Serializable;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name="forum_threads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumReply implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reply_id;

    @NonNull
    @NotNull
    @Column(length = 1000)
    private String message;

    @NonNull
    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private LocalDateTime sent_time;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user")
    private Users user;

    @ManyToOne
    private ForumThreads forumThread;

    public ForumReply(@NonNull @NotNull String message, @NonNull @NotNull LocalDateTime sent_time) {
        this.message = message;
        this.sent_time = sent_time;
    }
}
