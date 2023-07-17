package co.ke.unicite.Models;

import co.ke.unicite.Entities.RoomManagement.Courses;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UploadsModel implements Serializable {
    private Long id;

    private String fileName;


    private String fileType;


    private Long fileSize;


    private String filePath;


}
