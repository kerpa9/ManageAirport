package airportmanage.airport.Domain.DTOs.Update;

import airportmanage.airport.Domain.Enums.Airline;
import airportmanage.airport.Domain.Enums.PlaneStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record PlaneDTOU(

                Integer plane_number,
                String model,
                Integer max_capacity,
                @Enumerated(EnumType.STRING) Airline airline,
                @Enumerated(EnumType.STRING) PlaneStatus plane_status


) {

}
