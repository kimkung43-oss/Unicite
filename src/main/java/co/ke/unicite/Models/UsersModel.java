package co.ke.unicite.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsersModel implements Serializable {
    private Long userId;
    @NotBlank
    @Email(message = "Enter a valid email address")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private boolean isActive;

    @NotBlank
    private String roleCode;



}
