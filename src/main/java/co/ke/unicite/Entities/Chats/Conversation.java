package co.ke.unicite.Entities.Chats;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="conversations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversation_id;

    @Column(name="title", length = 100)
    private String title;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private Set<Messages> messages = new HashSet<>();

    public Conversation(String title, Set<Messages> messages) {
        this.title = title;
        this.messages = messages;
    }

    //    @NonNull
//    @NotNull
//    @Column(length = 255)
//    private String message;
//
//    @NonNull
//    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="timestamp")
//    private LocalDateTime timestamp;
//
//    @NonNull
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="sender_id")
//    private Users user;
}
