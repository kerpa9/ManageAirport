package airportmanage.airport.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.PassengerDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.PassengerRepositroy;
import airportmanage.airport.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepositroy passengerRepositroy;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Passenger createPassenger(@Valid PassengerDTO passengerDTO) {

        Passenger passenger = new Passenger();

        var user = userRepository.findById(passengerDTO.idUser()).get();

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
        passenger.setUser(user);
        passenger.setActive(passengerDTO.active());

        if (passengerDTO.ticket() != null) {
            passenger.setTickets(passengerDTO.ticket().stream()
                    .map(t -> new Tickets(loginId, loginId, seqPassenger, null, null, null, null, null, null, null))
                    .collect(Collectors.toList()));

        }

        if (passengerDTO.bookings() != null) {
            passenger.setBookings(passengerDTO.bookings().stream()
                    .map(b -> new Booking(loginId, loginId, seqPassenger, null, null, null, null, null, null,
                            passenger))
                    .collect(Collectors.toList()));

        }

        return passengerRepositroy.savePassengerWithRoles(passenger);

    }

    @Transactional
    public Page<Passenger> getAll(Pageable pageable) {

        Long userLogin = filterLoginService.getUserLogin();

        return passengerRepositroy.findAllActive(userLogin, pageable);

    }
}
