package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.TaskManagement.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findByUser(long Id);
    Notes existsByTitle(String title);

}
