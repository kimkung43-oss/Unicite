package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.CourseGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseGroupsRepository extends JpaRepository<CourseGroups, Long> {

    CourseGroups existsByName(String name);

    List<CourseGroups> findByCourse(long id);


}
