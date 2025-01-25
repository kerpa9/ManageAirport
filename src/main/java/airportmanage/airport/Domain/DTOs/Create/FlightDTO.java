package airportmanage.airport.Domain.DTOs.Create;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.FlightStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record FlightDTO(
        Long id_flight,
        Long origin_id,
        Long destination_id,
        Long idPlane,
        @NotNull LocalDateTime departure_time,
        LocalDateTime check_in_start,
        LocalDateTime check_in_end,
        Integer available_seats,
        @NotNull LocalDateTime created_at,
        @Enumerated(EnumType.STRING)
        FlightStatus flight_status,
        Boolean active

) {

    public FlightDTO {
        active = active == null ? true : active;
        created_at = LocalDateTime.now();
        flight_status = flight_status == null ? FlightStatus.pending : flight_status;

    }

}
