package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;
@Data
public class GradesModel implements Serializable {
    private Long id;

    private int score;

    private Long assignmentId;

    private Long userId;

    private Long groupId;
}
