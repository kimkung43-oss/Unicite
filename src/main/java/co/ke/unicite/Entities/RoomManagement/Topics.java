package co.ke.unicite.Entities.RoomManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
public class Topics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topic_id;

    @NotNull
    @NonNull
    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_course")
    private Courses courses;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private Set<Subtopics> subtopics = new HashSet<>();

    @OneToMany(mappedBy= "topic", cascade = CascadeType.ALL)
    private Set<CourseMaterials> courseMaterials = new HashSet<>();

    public Topics(@NotNull @NonNull String name, String description) {
        this.name = name;
        this.description = description;
    }
}
