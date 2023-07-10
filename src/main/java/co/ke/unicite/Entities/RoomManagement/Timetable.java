package co.ke.unicite.Entities.RoomManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name="timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timetable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private String day;

//    @Temporal(TemporalType.TIME)
    @Column(name = "start_time")
    private LocalTime startTime;

//    @Temporal(TemporalType.TIME)
    @Column(name = "end_time")
    private LocalTime endTime;

    // Mapping relationship with Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Courses course;

    public Timetable(String day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
