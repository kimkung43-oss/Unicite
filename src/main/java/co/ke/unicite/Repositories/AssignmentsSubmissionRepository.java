package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentsSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
//    List<AssignmentSubmission> findByAssignment(long id);
//    List<AssignmentSubmission> findByuser(long id);
//    List<AssignmentSubmission> findByCourseGroup(long id);
//    List<AssignmentSubmission> findByAssignmentAndCourseGroup(long id);
//    List<AssignmentSubmission> findByAssignmentAndUser(long id);




}
