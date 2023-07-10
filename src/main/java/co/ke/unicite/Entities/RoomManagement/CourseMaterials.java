package co.ke.unicite.Entities.RoomManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;


@Entity
@Data
@Table(name = "course_materials")
@AllArgsConstructor
@NoArgsConstructor
public class CourseMaterials implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @NonNull
        @Column
        private String title;

        @Column
        private String description;

        @NotNull
        @NonNull
        @Column
        private String filePath;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_course")
        private Courses course;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_topic")
        private Topics topic;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_subtopic")
        private Subtopics subtopic;

    public CourseMaterials(@NotNull @NonNull String title, String description, @NotNull @NonNull String filePath) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }
}
