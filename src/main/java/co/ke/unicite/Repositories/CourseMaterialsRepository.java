package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.CourseMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMaterialsRepository extends JpaRepository<CourseMaterials, Long> {
    CourseMaterials existsByName(String name);

    CourseMaterials findByFilePath(String path);

    List<CourseMaterials> findByCourse(long id);
    List<CourseMaterials> findByTopic(long id);
    List<CourseMaterials> findBySubtopic(long id);

}
