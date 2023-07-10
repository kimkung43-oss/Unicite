package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRecipientModel implements Serializable {
    private Long id;

    private Long userId;

    private Long groupId;

    private Long message;

    private boolean isRead;
}
