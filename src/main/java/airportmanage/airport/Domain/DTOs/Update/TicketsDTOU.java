package airportmanage.airport.Domain.DTOs.Update;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.TypeClass;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record TicketsDTOU(

        @Enumerated(EnumType.STRING) TypeClass type_class,
        Double price,
        Integer seat_number,
        LocalDateTime created_at) {

}
