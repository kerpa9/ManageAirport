package airportmanage.airport.Domain.DTOs;

import airportmanage.airport.Domain.Enums.RoleUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull @Email String email,
        @NotNull String password,
        @Enumerated(EnumType.STRING) RoleUser role_user) {

    public LoginDTO {
        role_user = role_user == null ? RoleUser.user : role_user;
        

    }

}
