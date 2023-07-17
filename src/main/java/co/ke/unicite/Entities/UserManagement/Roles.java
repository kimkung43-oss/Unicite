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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;


    @NotNull
    @NonNull
    @Column(name="roleName")
    private String roleName;


    private String code;

    public Roles(@NotNull @NonNull String roleName, String code) {
        this.roleName = roleName;
        this.code = code;
    }
}
