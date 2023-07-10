package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LogsModel implements Serializable {
    private Long logId;

    private String action;

    private LocalDateTime timestamp;

    private Long userId;
}
