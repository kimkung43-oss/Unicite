package co.ke.unicite.Services;

import co.ke.unicite.Entities.RoomManagement.CourseMaterials;
import co.ke.unicite.Exceptions.FileNotFoundException;
import co.ke.unicite.Exceptions.FileStorageException;
import co.ke.unicite.Exceptions.IOException;
import co.ke.unicite.Exceptions.ResourceNotFoundException;
import co.ke.unicite.Models.UploadsModel;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface CourseMaterialsService {
    CourseMaterials uploadCourseMaterial(UploadsModel uploadsModel, MultipartFile file) throws ResourceNotFoundException, FileStorageException;

    Resource downloadCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, FileNotFoundException;

    void deleteCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, IOException;
}