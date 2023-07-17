package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.UserManagement.Enrollments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollments,Long> {
//    List<Enrollments> findByUser(long id);

//    List<Enrollments> findByCourseAndIsActive(long id, boolean isActive);
//    List<Enrollments> findByUserAndIsActive(long id, boolean isActive);

//    List<Enrollments> findByIsActive(boolean active);

//    List<Enrollments> findByCourse(long id);
//    List<Enrollments> findByDate(LocalDate date);
}
