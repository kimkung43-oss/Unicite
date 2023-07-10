package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssignmentSubmissionModel implements Serializable {
    private Long id;

    private LocalDateTime submissionDate;

    private String filepath;
    private Long assignmentId;

    private Long userId;

    private Long groupId;
}
