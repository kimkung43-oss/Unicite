package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {

    List<Courses> findByUserId(String userId);
    Courses existsByName (String name);

    Courses existsByCourseCode(String courseCode);

    Courses existsByCourseTopicId(long courseId);

    Courses existsByCourseMaterialId(long courseMaterial);




}
