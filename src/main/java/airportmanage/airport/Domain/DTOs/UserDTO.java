package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.Genre;
import airportmanage.airport.Domain.Enums.RoleUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserDTO(

        Long id,
        @NotBlank String full_name,
        @NotBlank String email,
        @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])\\S{8,16}$") String password,
        @Enumerated(EnumType.STRING) @NotNull Genre genre,
        @Enumerated(EnumType.STRING) @NotNull RoleUser role_user,
        Boolean active,
        LocalDateTime created_at

) {
    public UserDTO {
        active = active == null ? true : active;
        role_user = role_user == null ? RoleUser.user : role_user;
    }

}
