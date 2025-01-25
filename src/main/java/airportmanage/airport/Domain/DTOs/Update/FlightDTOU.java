package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.FlightStatus;

public record FlightDTOU(

                LocalDateTime departure_time,
                LocalDateTime check_in_start,
                LocalDateTime check_in_end,
                Integer available_seats,
                FlightStatus flight_status,
                LocalDateTime created_at

) {

}
