package co.ke.unicite.Controllers;

import co.ke.unicite.Entities.RoomManagement.CourseMaterials;
import co.ke.unicite.Exceptions.FileStorageException;
import co.ke.unicite.Exceptions.ResourceNotFoundException;
import co.ke.unicite.Models.UploadsModel;
import co.ke.unicite.Services.CourseMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/courses/course materials")
public class CourseMaterialsController {
    @Autowired
    CourseMaterialsService courseMaterialsService;

    @PostMapping("/upload")
    public ResponseEntity<CourseMaterials> uploadCourseMaterials(@RequestBody UploadsModel uploadsModel,@RequestParam("file") MultipartFile file) throws ResourceNotFoundException, FileStorageException {
        CourseMaterials courseMaterials = courseMaterialsService.uploadCourseMaterial(uploadsModel, file);
        return ResponseEntity.ok().body(courseMaterials);
    }
}
