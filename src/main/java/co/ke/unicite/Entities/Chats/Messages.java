package co.ke.unicite.Entities.Chats;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="messages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    @Column(length = 255)
    private String message_body;

    @Column(name = "message_media")
    private String mediaUrl;

    @NonNull
    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sent_date_time")
    private LocalDateTime sentDateTime;


    @NonNull
    @NotNull
    @ManyToOne
    @JoinColumn(name="sender_id")
    private Users user;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name="parent_message_id")
    private Messages message;

    public Messages(String message_body, String mediaUrl, @NotNull LocalDateTime sentDateTime) {
        this.message_body = message_body;
        this.mediaUrl = mediaUrl;
        this.sentDateTime = sentDateTime;
    }
}
