package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.RoomManagement.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
//    Timetable findByCourse(long id);

//    List<Timetable> findByCourse(long id);

//    List<Timetable> findByDay(String day);


}
