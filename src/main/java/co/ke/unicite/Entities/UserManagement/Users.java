package co.ke.unicite.Entities.UserManagement;

import co.ke.unicite.Entities.RoomManagement.Courses;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @NonNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    //Ensure validation of email with regex, with an added lowercase constraint-remover
    @Column(length = 255, updatable = false, unique = true,columnDefinition = "TEXT")
    private String email;

    @NotNull
    @NonNull
    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 30, updatable = false, unique = true)
    private String username;

    @NotNull
    @NonNull
    @Column(name = "first_name", length = 30, updatable = false, columnDefinition = "TEXT")
    private String firstName;

    @NotNull
    @NonNull
    @Column(name = "last_name", length = 30, updatable = false,columnDefinition = "TEXT")
    private String lastName;

    @NotNull
    @Column(name="is_active")
    private Boolean isActive;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "role_id")
//    private Roles role;
    @ManyToMany
    @JoinTable(name= "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<Roles>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Courses> courses = new HashSet<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<CourseGroupMembers> courseGroupMember = new HashSet<>();
    @OneToMany
    @JoinTable(name = "CourseGroupMembers",
        joinColumns=@JoinColumn(name = "course_group_id"),
        inverseJoinColumns=@JoinColumn(name = "user_id")
    )
    private Set<Users> user = new HashSet<>();

    public Users(Long userId, @NotNull @NonNull String email, @NotNull @NonNull String password, String username, @NotNull @NonNull String firstName, @NotNull @NonNull String lastName, @NotNull Boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }
}
