package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseGroupMembersRepository extends JpaRepository<GroupMembers,Long> {
//    List<GroupMembers> findByCourseGroup(long id);
}
