package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Subtopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SubtopicsRepository extends JpaRepository<SubtopicsRepository, Long> {

    Subtopics existsByName(String name);
}
