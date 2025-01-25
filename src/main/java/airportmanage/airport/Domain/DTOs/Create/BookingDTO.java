package airportmanage.airport.Domain.DTOs.Create;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record BookingDTO(

        Long id_booking,
        @NotNull LocalDateTime booking_date,
        @NotNull Integer nro_tickets,
        @NotNull Double total_price,
        @NotNull LocalDateTime created_at,
        @Enumerated(EnumType.STRING)
        BookingStatus booking_status,
        Long idUser,
        Long idPassenger,
        Boolean active) {

    public BookingDTO {
        active = active == null ? true : active;
        booking_status = booking_status == null ? BookingStatus.pending : booking_status;

    }

}
