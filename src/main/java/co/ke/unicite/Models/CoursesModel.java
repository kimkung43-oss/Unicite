package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class CoursesModel implements Serializable {
    private Long courseId;

    @NotBlank
    private String courseName;

    @NotBlank
    private String courseCode;

    private String description;

    private String venueName;

    @NotBlank
    private Long userId;
}
