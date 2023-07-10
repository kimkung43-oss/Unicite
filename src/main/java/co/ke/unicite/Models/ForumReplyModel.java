package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ForumReplyModel implements Serializable {
    private Long reply_id;

    @NotBlank
    private String message;

    private LocalDateTime sent_time;

    private Long userId;

    private Long forumThreadId;
}
