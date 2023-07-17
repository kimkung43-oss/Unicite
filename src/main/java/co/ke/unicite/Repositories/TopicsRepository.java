package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
//    Topics exitsByName(String name);
//
//    Topics findDescriptionByTopicId(String courseId);

}
