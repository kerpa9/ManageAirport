package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

public record BookingDTOU(

        Integer nro_tickets,
        Double total_price,
        LocalDateTime created_at) {

}
