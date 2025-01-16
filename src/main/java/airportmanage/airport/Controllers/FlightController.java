package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.FlightDTO;
import airportmanage.airport.Domain.DTOs.PageableDTO;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Services.FlightService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/flight")

public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody @Valid FlightDTO flightDTO) {
        try {

            Flight createFlight = flightService.createFlight(flightDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createFlight);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    @SuppressWarnings("rawtypes")
    @GetMapping
    private ResponseEntity<PageableDTO> getAllFlight(@PageableDefault(size = 5) Pageable pageable) {

        Page<Flight> flight = flightService.getAllFlights(pageable);

        Page<FlightDTO> flightDTO = flight.map(
                f -> new FlightDTO(f.getId_flight(), f.getId_login(), f.getDestination().getId(), f.getPlane().getId(),
                        f.getDeparture_time(), f.getCheck_in(), f.getCreated_at(), f.getActive()));

        return ResponseEntity.ok(PageableDTO.fromPage(flightDTO));
    }

}
