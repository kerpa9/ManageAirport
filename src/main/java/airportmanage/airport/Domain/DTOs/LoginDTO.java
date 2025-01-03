package airportmanage.airport.Domain.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull @Email String email,
        @NotNull String password,
        @NotNull Boolean active) {

    public LoginDTO {
        active = active == null ? true : active;

    }

}
