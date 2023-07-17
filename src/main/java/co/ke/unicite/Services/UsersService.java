package co.ke.unicite.Services;

import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Models.UsersModel;

public interface UsersService {
    Users findById(long userId);

    Users createUser(UsersModel usersModel);


    Users updateUser(UsersModel usersModel);

    Users deleteUser(long userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String userName);
    

    boolean existsById(Long userId);
}
