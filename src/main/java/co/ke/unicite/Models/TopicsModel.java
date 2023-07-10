package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class TopicsModel implements Serializable {
    private Long topic_id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private Long courseId;
}
