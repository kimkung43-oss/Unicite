package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TasksModel implements Serializable {
    private Long task_id;

    @NotBlank
    private String title;


    private String description;

    private LocalDate createdDate;

    private Boolean isCompleted;

    private Long userId;


}
