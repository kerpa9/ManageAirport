package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.Airline;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record PlaneDTO(

        Long id_plane,
        @NotNull Integer plane_number,
        @NotNull String model,
        @NotNull Integer max_capacity,
        @NotNull @Enumerated(EnumType.STRING) Airline airline,
        LocalDateTime created_at,
        Boolean active

) {
    public PlaneDTO {
        active = active == null ? true : active;

    }

}
