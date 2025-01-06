package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.FlightDTO;
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

}
