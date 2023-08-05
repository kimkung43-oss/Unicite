package co.ke.unicite.Services;

import co.ke.unicite.Entities.RoomManagement.Courses;
import co.ke.unicite.Models.CoursesModel;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Courses> getAllCourses();

    List<Courses> getCoursesByUser(Long userId);

    Courses getCourseById(Long id);

    Courses createCourse(CoursesModel coursesModel);

    Courses updateCourse(CoursesModel coursesModel);

    Courses deleteCourse(Long id);

    boolean existsByCourseName(String name);

    Optional<Courses> findById(Long id);

}
