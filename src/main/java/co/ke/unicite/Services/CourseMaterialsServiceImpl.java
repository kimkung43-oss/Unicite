package co.ke.unicite.Services;

import co.ke.unicite.Entities.RoomManagement.CourseMaterials;
import co.ke.unicite.Entities.RoomManagement.Courses;
import co.ke.unicite.Entities.RoomManagement.Uploads;
import co.ke.unicite.Exceptions.FileNotFoundException;
import co.ke.unicite.Exceptions.FileStorageException;
import co.ke.unicite.Exceptions.IOException;
import co.ke.unicite.Exceptions.ResourceNotFoundException;
import co.ke.unicite.Models.UploadsModel;
import co.ke.unicite.Repositories.CourseMaterialsRepository;
import co.ke.unicite.Repositories.CoursesRepository;
import co.ke.unicite.Repositories.UploadsRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class CourseMaterialsServiceImpl implements CourseMaterialsService {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private UploadsRepository uploadsRepository;

    @Autowired
    private CourseMaterialsRepository courseMaterialsRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public CourseMaterials uploadCourseMaterial(UploadsModel uploadsModel, MultipartFile file) throws ResourceNotFoundException, FileStorageException {
        try {
            // Retrieve the course from the database using the courseId
//            Courses course = coursesRepository.findById(courseId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));
            Courses course = coursesRepository.findById(uploadsModel.getId()).get();

            // Generate a unique file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String[] parts = fileName.split("\\.");
            String fileExtension = parts[parts.length - 1];
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

            // Save the file to the server
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(uniqueFileName).normalize();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create a new Uploads entity and set its properties
            Uploads upload = new Uploads();
            upload.setFileName(uploadsModel.getFileName());
            upload.setFileType(uploadsModel.getFileType());
            upload.setFileSize(uploadsModel.getFileSize());
            upload.setFilePath(uploadsModel.getFilePath().toString());

            // Save the new Uploads entity to the database
            Uploads savedUpload = uploadsRepository.save(upload);

            // Create a new CourseMaterials entity and set its properties
            CourseMaterials courseMaterials = new CourseMaterials();
            courseMaterials.setCourse(course);
            courseMaterials.setUpload(savedUpload);

            // Save the new CourseMaterials entity to the database
            CourseMaterials savedCourseMaterials = courseMaterialsRepository.save(courseMaterials);

            // Return the saved CourseMaterials entity
            return savedCourseMaterials;
        } catch (ResourceNotFoundException ex){
            throw ex;
        } catch (Exception ex) {
            // Handle other exceptions and wrap them in a custom exception class
            throw new FileStorageException("An error occurred while uploading the file", ex);
        }
    }

    @Override
    public Resource downloadCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, FileNotFoundException {
        return null;
    }

    @Override
    public void deleteCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, IOException {

    }

//    @Override
//    public Resource downloadCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, FileNotFoundException {
//        // Retrieve the course material from the database using the courseMaterialId
//        CourseMaterials courseMaterials = courseMaterialsRepository.findById(courseMaterialId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course material not found with ID: " + courseMaterialId));
//
//        // Get the file path from the course material object
//        String filePath = courseMaterials.getFilePath();
//
//        // Load the file as a resource
//        Resource resource = fileStorageService.loadFileAsResource(filePath);
//
//        if (resource == null) {
//            throw new FileNotFoundException("File not found: " + filePath);
//        }
//
//        return resource;
//    }
//
//    @Override
//    public void deleteCourseMaterial(Long courseMaterialId) throws ResourceNotFoundException, IOException {
//        // Retrieve the course material from the database using the courseMaterialId
//        CourseMaterials courseMaterials = courseMaterialsRepository.findById(courseMaterialId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course material not found with ID: " + courseMaterialId));
//
//        // Get the file path from the course material object
//        String filePath = courseMaterials.getFilePath();
//
//        // Delete the file from the server
//        boolean isDeleted = fileStorageService.deleteFile(filePath);
//
//        if (!isDeleted) {
//            throw new IOException("Failed to delete file: " + filePath);
//        }
//
//        // Delete the course material record from the database
//        courseMaterialsRepository.delete(courseMaterials);
//    }
}