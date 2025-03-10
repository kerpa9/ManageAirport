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
import airportmanage.airport.Domain.DTOs.Create.PassengerDTO;
import airportmanage.airport.Domain.DTOs.Update.PassengerDTOU;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Services.PassengerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/passenger")

public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Passenger> registerPassenger(@RequestBody @Valid PassengerDTO passengerDTO) {

        try {

            Passenger createPassenger = passengerService.createPassenger(passengerDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createPassenger);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        }

    }

    @SuppressWarnings("rawtypes")
    @GetMapping
    private ResponseEntity<PageableDTO> getallPassengers(@PageableDefault(size = 5) Pageable pageable) {
        try {

            Page<Passenger> passenger = passengerService.getAllPassengers(pageable);

            Page<PassengerDTO> passengerDTO = passenger
                    .map(p -> new PassengerDTO(p.getId(), p.getFirst_name(), p.getLast_name(), p.getNro_passport(),
                            p.getBorn_date(),
                            p.getGenre(), p.getEmail(), p.getPassword(), p.getUserId(), p.getTickets(), p.getBookings(),
                            p.getPhone(), p.getActive()));

            return ResponseEntity.ok(PageableDTO.fromPage(passengerDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/{id}")
    public Optional<Passenger> getOneById(@PathVariable @Valid Long id) {
        try {

            return passengerService.getOneById(id);
        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @DeleteMapping("/{id}")
    public Optional<Optional<Passenger>> softDelete(@PathVariable @Valid Long id) {

        try {
            return passengerService.softdelete(id);
        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Passenger>> update(@RequestBody @Valid PassengerDTOU passengerDTOU,
            @PathVariable @Valid Long id) {

        try {
            Optional<Passenger> update = passengerService.updatePassenger(passengerDTOU, id);

            if (update.isPresent()) {

                return ResponseEntity.status(HttpStatus.OK).body(update);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);

        }

    }
}
