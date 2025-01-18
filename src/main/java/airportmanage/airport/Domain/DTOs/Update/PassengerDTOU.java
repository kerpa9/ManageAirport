package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;
import airportmanage.airport.Config.Security.HashPassword;
import airportmanage.airport.Domain.Enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;

public record PassengerDTOU(

        String first_name,
        String last_name,
        LocalDateTime born_date,
        @Enumerated(EnumType.STRING) Genre genre,
        @Email String email,
        String password,
        Long idUser,
        String phone) {

    public PassengerDTOU {

        HashPassword hash = new HashPassword();
        password = hash.hashingPass(password);

    }

}
