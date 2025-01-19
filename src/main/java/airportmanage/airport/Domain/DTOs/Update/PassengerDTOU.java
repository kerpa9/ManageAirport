package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;
import airportmanage.airport.Config.Security.HashPassword;
import airportmanage.airport.Domain.Enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record PassengerDTOU(

        String first_name,
        String last_name,
        LocalDateTime born_date,
        @Enumerated(EnumType.STRING) Genre genre,
        String password,
        String phone) {

    public PassengerDTOU {

        HashPassword hash = new HashPassword();
        password = hash.hashingPass(password);

    }

}
