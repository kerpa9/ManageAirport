package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;

import airportmanage.airport.Domain.Enums.TypeClass;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record TicketsDTO(
        Long id_ticket,
        @NotNull @Enumerated(EnumType.STRING) TypeClass type_class,
        @NotNull Double price,
        @NotNull Integer seat_number,
        Boolean active,
        LocalDateTime created_at) {

}
