package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessagesModel implements Serializable {

    private Long messageId;

    @NotBlank
    private String messageBody;

    private String mediaUrl;

    private LocalDateTime sentDateTime;

    private Long userId;

    private Long conversationId;

    private Long parentMessageId;
}
