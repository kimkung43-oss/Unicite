package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
//    List<Logs> findByUser(long id);
}
