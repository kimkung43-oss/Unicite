package co.ke.unicite.Services;

import co.ke.unicite.Entities.UserManagement.Roles;
import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Models.UsersModel;

import java.util.List;

public interface UsersService {
    Users findById(long userId);

    Users createUser(UsersModel usersModel);


    List<Users> getUsers();
    Users updateUser(UsersModel usersModel);

    Users deleteUser(long userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String userName);

    List<Users> getUsersByRole(Long roleId);
    

    boolean existsById(Long userId);
}
