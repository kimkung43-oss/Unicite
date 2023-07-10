package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubtopicsModel implements Serializable {
    private Long subtopics_id;
    @NotBlank
    private String subtopic_name;

    private Long topicId;
}
