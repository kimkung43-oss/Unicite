package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Courses;
import co.ke.unicite.Entities.UserManagement.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    List<Courses> findByUser(Users user);


    Courses findByCourseCode(String courseCode);

    List<Courses> findAll(Sort sort);

    Page<Courses> findAll(Pageable pageable);

    Long countByUser(Users user);

    boolean existsByCourseName(String courseName);
}
