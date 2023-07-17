package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Uploads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadsRepository extends JpaRepository<Uploads, Long> {
}