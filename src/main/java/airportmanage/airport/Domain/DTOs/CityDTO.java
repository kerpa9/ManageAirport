package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import airportmanage.airport.Domain.Models.Flight;
import jakarta.validation.constraints.NotNull;

public record CityDTO(

        Long id_city,
        @NotNull String name,
        @NotNull String country,
        @NotNull Double lat,
        @NotNull Double lon,
        @NotNull LocalDateTime created_at,
        List<Flight> flights,
        Boolean active

) {
    public CityDTO {
        active = active == null ? true : active;
    }

}
