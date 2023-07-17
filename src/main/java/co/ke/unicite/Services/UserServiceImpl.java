package co.ke.unicite.Services;

import co.ke.unicite.Entities.UserManagement.Roles;
import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Exceptions.ResourceNotFoundException;
import co.ke.unicite.Models.UsersModel;
import co.ke.unicite.Repositories.RoleRepository;
import co.ke.unicite.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository rolesRepository;

    @Override
    public Users findById(long userId) {
        return usersRepository.findById(userId).get();
    }

    @Override
    public Users createUser(UsersModel usersModel) {
        Users savedUser = new Users();
        Users user = new Users();
        user.setEmail(usersModel.getEmail());
        user.setFirstName(usersModel.getFirstName());
        user.setLastName(usersModel.getLastName());
        user.setUsername(usersModel.getUsername());
        user.setPassword(usersModel.getPassword());
        user.setIsActive(true);
//
//
        Set<Roles> roles = new HashSet<>();

        Roles userRole = rolesRepository.findByCode(usersModel.getRoleCode());
        roles.add(userRole);
        user.setRoles(roles);

        savedUser = usersRepository.save(user);
        return savedUser;
    }

    @Override
    public boolean existsByEmail(String email) {
        return (usersRepository.existsByEmail(email));
    }


    @Override
    public boolean existsByUsername(String userName) {
        return (usersRepository.existsByUsername(userName));
    }


    @Override
    public Users updateUser(UsersModel usersModel) {
        Users user = usersRepository.findById(usersModel.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + usersModel.getUserId()));
        Users savedUser = new Users();

        user.setFirstName(usersModel.getFirstName());
        user.setLastName(usersModel.getLastName());
        user.setUsername(usersModel.getUsername());
        user.setPassword(usersModel.getPassword());
        user.setIsActive(true);
//            Set<Roles> roles = new HashSet<>();

//			Roles userRole = rolesRepository.findByCode(usersModel.getRoleCode());
//			roles.add(userRole);
//			user.setRoles(roles);
        savedUser = usersRepository.save(user);


        return savedUser;

    }

    @Override
    public Users deleteUser(long userId) {
        Users user = usersRepository.findById(userId).get();
        user.setIsActive(false);
        return usersRepository.save(user);
    }


    @Override
    public boolean existsById(Long userId) {
        // TODO Auto-generated method stub
        return usersRepository.existsById(userId);
    }
}
