package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record BookingDTOU(

                Integer nro_tickets,
                Double total_price,
                @Enumerated(EnumType.STRING)
                BookingStatus booking_status,
                LocalDateTime created_at) {

}
