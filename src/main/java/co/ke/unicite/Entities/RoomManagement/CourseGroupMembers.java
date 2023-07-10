package co.ke.unicite.Entities.RoomManagement;

import co.ke.unicite.Entities.UserManagement.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Table(name="course_group_members")
@AllArgsConstructor
@NoArgsConstructor
public class CourseGroupMembers implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupMemberId;

    // Mapping relationship with User
    @ManyToOne(cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // Mapping relationship with Group
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_group")
    private CourseGroups courseGroup;





}
