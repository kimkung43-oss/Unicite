package co.ke.unicite.Entities.RoomManagement;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private Long courseId;

    @Column(length = 255, nullable = false, columnDefinition = "TEXT")
    private String courseName;

    @Column(length = 255, nullable = false)
    private String courseCode;

    @Column(length = 255)
    private String description;

    @Column(length = 30)
    private String venueName;

    @ManyToOne
    @JoinColumn(name="fk_user_id")
    private Users user;

    @OneToMany(mappedBy = "courses",cascade = CascadeType.ALL)
    private HashSet<Topics> topics = new HashSet<>();


    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private Set<CourseMaterials> courseMaterials= new HashSet<>();

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    private Set<CourseGroups> courseGroups = new HashSet<>();

    public Courses(String courseName, String courseCode, String description, String venueName) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.description = description;
        this.venueName = venueName;
    }
}
