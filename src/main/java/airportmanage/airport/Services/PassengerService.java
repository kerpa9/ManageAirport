package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.PassengerDTO;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Repository.PassengerRepositroy;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepositroy passengerRepositroy;

    @Autowired
    private FilterLoginService filterLoginService;

    @Transactional
    public Passenger createPassenger(@Valid PassengerDTO passengerDTO) {

        Passenger passenger = new Passenger();
        Long loginId = filterLoginService.getUserLogin();

        Long seqPassenger = passengerRepositroy.generatedInsertSequential(loginId) + 1;

        passenger.setId_login(loginId);
        passenger.setId_passenger(seqPassenger);
        passenger.setFirst_name(passengerDTO.first_name());
        passenger.setLast_name(passengerDTO.last_name());
        passenger.setBorn_date(passengerDTO.born_date());
        passenger.setGenre(passengerDTO.genre());
        passenger.setPhone(passengerDTO.phone());
        passenger.setEmail(passengerDTO.email());
        passenger.setPassword(passengerDTO.password());
        passenger.setActive(passengerDTO.active());

        return passengerRepositroy.savePassengerWithRoles(passenger);

    }
}
