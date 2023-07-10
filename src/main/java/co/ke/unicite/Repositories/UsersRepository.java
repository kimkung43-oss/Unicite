package co.ke.unicite.Repositories;

import co.ke.unicite.Entities.UserManagement.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByRole(Long role);
    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
    List<Users> findByIsActive(boolean active);

}
