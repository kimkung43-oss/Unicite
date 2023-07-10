package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Announcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementsRepository extends JpaRepository<Announcements, Long>{
    Announcements existsByTitle(String title);
    Announcements existsByTitleAndIsActive(String title, boolean isActive);
    List<Announcements> findByCourse(long id);

    List<Announcements> findByCourseAndIsActive(long id, boolean isActive);
}
