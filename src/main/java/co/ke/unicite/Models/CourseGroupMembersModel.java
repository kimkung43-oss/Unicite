package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseGroupMembersModel implements Serializable {
    private Long groupMemberId;

    private Long userId;

    private Long groupId;
}
