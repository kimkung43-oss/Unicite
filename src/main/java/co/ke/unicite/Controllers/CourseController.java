package co.ke.unicite.Controllers;

import co.ke.unicite.Entities.RoomManagement.Courses;
import co.ke.unicite.Models.CoursesModel;
import co.ke.unicite.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCourse(@RequestBody CoursesModel coursesModel) {
        if (coursesModel.getCourseName() == "" || coursesModel.getCourseName() == null) {
            return ResponseEntity.badRequest().body("Please enter the name of this course.");

        } else if (coursesModel.getCourseCode() == "" || coursesModel.getCourseCode() == null) {
            return ResponseEntity.badRequest().body("Please the course code.");

        }
        if (courseService.existsByCourseName(coursesModel.getCourseName())) {
            return ResponseEntity.badRequest().body("Course already exists. Please enter another course name");
        }

        Courses savedCourse = courseService.createCourse(coursesModel);

        return ResponseEntity.ok(savedCourse);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCourse(@RequestBody CoursesModel coursesModel, Long courseId) {
        courseService.updateCourse(coursesModel);

        return ResponseEntity.ok("User updated succesfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCourse(@RequestParam long courseId) {
        courseService.findById(courseId);

        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted succesfully");
    }
}
