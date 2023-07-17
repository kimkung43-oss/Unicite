package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.UserManagement.Roles;
import co.ke.unicite.Entities.UserManagement.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByRoles(Roles role);
    boolean existsByEmail(String email);

   boolean existsByUsername(String username);
    List<Users> findByIsActive(boolean active);

}
