package co.ke.unicite.Entities.RoomManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="course_groups")
@AllArgsConstructor
@NoArgsConstructor
public class CourseGroups implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_group_id",updatable = false, nullable = false)
    private Long course_group_id;


    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_course")
    private Courses course;

    // Mapping relationship with GroupMember
    @OneToMany(mappedBy = "courseGroup", fetch= FetchType.LAZY)
    private Set<GroupMembers> members=new HashSet<>();

    public CourseGroups(String name) {
        this.name = name;
    }
}
