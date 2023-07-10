package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GradesRepository extends JpaRepository<Grades, Long> {
    List<Grades> findByUser(long id);

    List<Grades> findByScoreIsAfter(int score);
    List<Grades> findByCourse(long id);

    List<Grades> findByGroup(long id);

    List<Grades> findByUserAndCourse(long id, long courseId);
    List<Grades> findByGroupAndCourse(long groupId, long courseId);
}
