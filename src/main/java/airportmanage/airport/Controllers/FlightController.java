package airportmanage.airport.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Config.HandleException.HandleException;
import airportmanage.airport.Domain.DTOs.Configure.PageableDTO;
import airportmanage.airport.Domain.DTOs.Create.FlightDTO;
import airportmanage.airport.Domain.DTOs.Update.FlightDTOU;
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

        try {
            Page<Flight> flight = flightService.getAllFlights(pageable);

            Page<FlightDTO> flightDTO = flight.map(
                    f -> new FlightDTO(f.getId_flight(), f.getOrigin().getId(), f.getDestination().getId(),
                            f.getPlane().getId(),
                            f.getDeparture_time(), f.getCheck_in_start(), f.getCreated_at(), f.getActive()));

            return ResponseEntity.ok(PageableDTO.fromPage(flightDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Flight>> getById(@PathVariable @Valid Long id) {
        try {
            if (flightService.getOneById(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(flightService.getOneById(id));

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {

            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Optional<Flight>>> softDelete(@PathVariable @Valid Long id) {
        try {
            if (flightService.softDelete(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(flightService.softDelete(id));

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Flight>> update(@RequestBody @Valid FlightDTOU flightDTOU, @PathVariable Long id) {

        try {
            Optional<Flight> update = flightService.updateFlight(flightDTOU, id);
            if (update.isPresent()) {

                return ResponseEntity.status(HttpStatus.OK).body(update);

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }

    }
}
