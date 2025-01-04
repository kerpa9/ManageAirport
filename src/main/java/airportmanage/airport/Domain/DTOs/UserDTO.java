package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import airportmanage.airport.Config.Security.HashPassword;
import airportmanage.airport.Domain.Enums.Genre;
import airportmanage.airport.Domain.Enums.RoleUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

        Long id,
        @NotBlank String full_name,
        @NotBlank  @Email String email,
        @NotBlank 
        String password,
        @Enumerated(EnumType.STRING) @NotNull Genre genre,
        @NotNull
        @Enumerated(EnumType.STRING) @NotNull RoleUser role_user,
        Boolean active,
        LocalDateTime created_at

) {
    public UserDTO {
        HashPassword hash = new HashPassword();
        password = hash.hashingPass(password);
        active = active == null ? true : active;
        role_user = role_user == null ? RoleUser.user : role_user;
    }

}
