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
@Table(name = "subtopics")
@AllArgsConstructor
@NoArgsConstructor
public class Subtopics implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subtopics_id;

    @NotNull
    @NonNull
    @Column(length = 255)
    private String subtopic_name;

    @NotNull
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_topic_id")
    private Topics topic;

    @OneToMany(mappedBy = "subtopic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CourseMaterials> courseMaterials = new HashSet<>();


    public Subtopics(@NotNull @NonNull String subtopic_name) {
        this.subtopic_name = subtopic_name;
    }
}
