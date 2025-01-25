package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.FlightStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record FlightDTOU(

                LocalDateTime departure_time,
                LocalDateTime check_in_start,
                LocalDateTime check_in_end,
                Integer available_seats,
                @Enumerated(EnumType.STRING) FlightStatus flight_status,
                LocalDateTime created_at

) {

}
