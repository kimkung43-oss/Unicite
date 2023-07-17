package co.ke.unicite.Controllers;

import co.ke.unicite.Entities.UserManagement.Users;
import co.ke.unicite.Models.UsersModel;
import co.ke.unicite.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UsersService usersService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UsersModel usersModel) {

        if (usersModel.getEmail() == "" || usersModel.getEmail() == null) {
            return ResponseEntity.badRequest().body("Please enter your email.");

        } else if (usersModel.getFirstName() == "" || usersModel.getFirstName() == null) {
            return ResponseEntity.badRequest().body("Please your first name.");

        } else if (usersModel.getLastName() == "" || usersModel.getLastName() == null) {
            return ResponseEntity.badRequest().body("Please enter your last name.");

        }
        if (usersService.existsByEmail(usersModel.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        if (usersService.existsByUsername(usersModel.getUsername())) {
            return ResponseEntity.badRequest().body("Username is taken. Please enter a new one");
        }

        Users savedUser = usersService.createUser(usersModel);


        return ResponseEntity.ok(savedUser);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UsersModel usersModel) {
        Users updatedUser = usersService.updateUser(usersModel);

        return ResponseEntity.ok("User updated succesfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam long userId) {
        Users user = usersService.findById(userId);

//            if (user.isAdmin()) {
//                return ResponseEntity.badRequest().body("Error: Cannot delete user.");
//            }

        Users deletedUser = usersService.deleteUser(userId);
        return ResponseEntity.ok("User deleted succesfully");
    }

}


