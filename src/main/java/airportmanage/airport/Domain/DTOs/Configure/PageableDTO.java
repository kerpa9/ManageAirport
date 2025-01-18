package airportmanage.airport.Domain.DTOs.Configure;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageableDTO<T>(List<T> content) {
    public static <T> PageableDTO<T> fromPage(Page<T> page) {
        return new PageableDTO<>(page.getContent());

    }

}
