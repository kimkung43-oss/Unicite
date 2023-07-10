package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class TimetableModel implements Serializable {
    private Long id;
    @NotBlank
    private String day;

    @NotBlank
    private LocalTime startTime;

    @NotBlank
    private LocalTime endTime;

    private Long courseId;
}
