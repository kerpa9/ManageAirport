package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record BookingDTO(

        Long id_booking,
        @NotNull LocalDateTime booking_date,
        // Long id_passenger,
        // Long id_flight,
        @NotNull Integer nro_tickets,
        @NotNull Double total_price,
        @NotNull LocalDateTime created_at,
        Long idUser,
        Long idPassenger,
        // Integer created_by,
        Boolean active) {

    public BookingDTO {
        active = active == null ? true : active;
    }

}
