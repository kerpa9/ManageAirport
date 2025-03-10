package airportmanage.airport.Domain.DTOs.Update;

import airportmanage.airport.Domain.Enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record UserDTOU(

        String full_name,
        @Enumerated(EnumType.STRING) @NotNull Genre genre

) {

}
