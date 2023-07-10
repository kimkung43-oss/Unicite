package co.ke.unicite.Entities.UserManagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
@Entity
@Data
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "roleIdSequence",
            sequenceName = "roleIdSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roleIdSequence"
    )
    @Column(name="roleId")
    private Long roleId;


    @NotNull
    @NonNull
    @Column(name="roleName",updatable = false,insertable = false)
    private String roleName;


    private String code;

    public Roles(@NotNull @NonNull String roleName, String code) {
        this.roleName = roleName;
        this.code = code;
    }
}
