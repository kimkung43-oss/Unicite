package co.ke.unicite.Entities.Chats;

import co.ke.unicite.Entities.RoomManagement.CourseGroupMembers;
import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Entity
@Table(name="messages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessageRecipient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="fk_reciever_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name= "fk_receiver_group_id")
    private CourseGroupMembers courseGroupMembers;

    @ManyToOne
    @JoinColumn(name="fk_message_id")
    private Messages message;

    @NonNull
    @NotNull
    @Column(name="is_read")
    private Boolean isRead;

    public MessageRecipient(@NotNull Boolean isRead) {
        this.isRead = isRead;
    }
}
