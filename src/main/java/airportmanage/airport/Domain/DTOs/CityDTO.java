package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record CityDTO(

        Long id_city,
        @NotNull String name,
        @NotNull String country,
        @NotNull Double lat,
        @NotNull Double lon,
        @NotNull LocalDateTime created_at,
        Boolean active

) {
    public CityDTO {
        active = active == null ? true : active;
    }

}
