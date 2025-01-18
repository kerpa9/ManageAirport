package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;
import airportmanage.airport.Domain.Enums.Airline;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record PlaneDTOU(

        Integer plane_number,
        String model,
        Integer max_capacity,
        @Enumerated(EnumType.STRING) Airline airline,
        LocalDateTime created_at

) {

}
