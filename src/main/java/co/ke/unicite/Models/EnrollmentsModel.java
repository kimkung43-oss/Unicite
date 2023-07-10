package co.ke.unicite.Models;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class EnrollmentsModel implements Serializable {
    private Long enrollmentId;

    private Boolean isActive;
    private LocalDate enrolledDate;

    private Long userId;

    private Long courseId;
}
