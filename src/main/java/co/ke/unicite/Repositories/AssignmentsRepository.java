package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assignments, Long> {

//    Assignments existsByTitle(String title);
//
//    Assignments findByIsActive(boolean isActive);
//    @Query("SELECT * FROM Assignments a WHERE a.course_id =:id")
//    List<Assignments> findByCourse(long id);
//    @Query("SELECT a FROM Assignments a WHERE a.user_id =:id")
//    List<Assignments> findByUser(long id);

//    List<Assignments> findByCourseAndUser(long courseId, long userId);

}
