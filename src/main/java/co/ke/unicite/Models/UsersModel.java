package co.ke.unicite.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsersModel implements Serializable {
    private Long userId;
    @NotBlank(message = "Enter an email")
    @Email(message = "Enter a valid email address",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotBlank
    private String password;
    @NotBlank(message = "Enter a username")
    private String username;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private boolean isActive;

    @NotBlank
    private String roleCode;



}
