package co.ke.unicite.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class NotesModel implements Serializable {
    private Long note_id;

    @NotBlank
    private String title;

    private String content;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    private Long userId;
}
