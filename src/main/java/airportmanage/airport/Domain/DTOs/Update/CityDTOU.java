package airportmanage.airport.Domain.DTOs.Update;
import java.time.LocalDateTime;

public record CityDTOU(

        String name,
        String country,
        Double lat,
        Double lon,
        LocalDateTime created_at

) {

}
