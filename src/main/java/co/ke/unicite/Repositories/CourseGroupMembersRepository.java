package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.CourseGroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseGroupMembersRepository extends JpaRepository<CourseGroupMembers,Long> {
    List<CourseGroupMembers> findByCourseGroup(long id);
}
