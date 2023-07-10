package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseMaterialsModel implements Serializable {

    private Long id;
    @NotBlank
    private String title;

    private String description;

    private String filePath;

    private Long courseId;

    private Long topicId;

    private Long subtopicId;
}
