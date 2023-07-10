package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ForumThreadsModel implements Serializable {
    private Long thread_id;

    @NotBlank
    private String threadTitle;

    private LocalDateTime timestamp;

    private Boolean isActive;

    private Long userId;
}
