package com.backend.Login.Auth;
import com.backend.Login.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String firstname;
    String lastname;
    String dni;
    Role role;
}
