package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

public record FlightDTOU(

        LocalDateTime departure_time,
        LocalDateTime check_in,
        LocalDateTime created_at

) {

}
