package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AnnouncementsModel implements Serializable {
    private Long announcement_id;

    @NotBlank
    private String title;

    private String description;

    private LocalDate createdDate;
    private LocalDate scheduledDate;
    private Boolean isActive;

    private Long courseId;
}
