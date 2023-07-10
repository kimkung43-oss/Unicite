package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;
@Data
public class CourseGroupsModel implements Serializable {
    private Long courseGroupId;

    private String name;

    private Long courseId;
}
