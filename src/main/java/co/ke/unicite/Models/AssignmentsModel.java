package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AssignmentsModel implements Serializable {

    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private LocalDateTime deadline;

    private boolean isActive;

    private Long courseId;

    private Long groupId;
}
