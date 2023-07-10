package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;


@Data
public class ConversationsModel implements Serializable {
    private Long conversationId;

    @NotBlank
    private String title;

}
