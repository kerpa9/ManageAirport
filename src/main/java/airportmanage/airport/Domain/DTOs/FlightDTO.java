package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record FlightDTO(
        Long id_flight,
        Long origin_id,
        Long destination_id,
        Long idPlane,
        @NotNull LocalDateTime departure_time,
        @NotNull LocalDateTime check_in,
        @NotNull LocalDateTime created_at,
        Boolean active

) {

    public FlightDTO {
        active = active == null ? true : active;
    }

}
