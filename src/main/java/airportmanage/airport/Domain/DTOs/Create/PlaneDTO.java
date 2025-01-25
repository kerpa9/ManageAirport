package airportmanage.airport.Domain.DTOs.Create;

import java.time.LocalDateTime;
import java.util.List;

import airportmanage.airport.Domain.Enums.Airline;
import airportmanage.airport.Domain.Enums.PlaneStatus;
import airportmanage.airport.Domain.Models.Flight;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record PlaneDTO(

        // Long id_plane,
        @NotNull Integer plane_number,
        @NotNull String model,
        @NotNull Integer max_capacity,
        List<Flight> flight,
        @NotNull @Enumerated(EnumType.STRING) Airline airline,
        LocalDateTime created_at,
        @Enumerated(EnumType.STRING) PlaneStatus plane_status,
        Boolean active

) {
    public PlaneDTO {
        active = active == null ? true : active;
        created_at = LocalDateTime.now();
        plane_status = plane_status == null ? PlaneStatus.active : plane_status;

    }

}
