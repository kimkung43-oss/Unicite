package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.UserManagement.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    List<Roles> findByName(String roleName);
    Roles findByCode(String code);
    boolean existsByCode(String string);
}
