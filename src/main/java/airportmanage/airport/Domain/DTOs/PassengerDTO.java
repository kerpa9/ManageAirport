package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import airportmanage.airport.Config.Security.HashPassword;
import airportmanage.airport.Domain.Enums.Genre;
import airportmanage.airport.Domain.Models.Tickets;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PassengerDTO(
        Long id_passenger,
        @NotNull String first_name,
        @NotNull String last_name,
        LocalDateTime born_date,
        @NotNull @Enumerated(EnumType.STRING) Genre genre,
        @NotNull @Email String email,
        @NotNull String password,
        Long idUser,
        List<Tickets> ticket,
        String phone,
        Boolean active) {

    public PassengerDTO {

        active = active == null ? true : active;
        HashPassword hash = new HashPassword();
        password = hash.hashingPass(password);

    }

}
