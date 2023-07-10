package co.ke.unicite.Services;

import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Models.UsersModel;

public interface UsersService {
    Users findById(long userId);

    Users createUser(UsersModel signUpModel);


    Users updateUser(UsersModel signUpModel);

    Users deleteUser(long userId);

    boolean existsByEmail(String email);

    boolean existsByUserName(String phoneNo);

    Users createChildUser(UsersModel signUpChildModel);

    boolean existsById(Long userId);
}
