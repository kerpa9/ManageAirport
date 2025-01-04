package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.PassengerDTO;
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

    @GetMapping
    public ResponseEntity response() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Funciona");
    }

}
