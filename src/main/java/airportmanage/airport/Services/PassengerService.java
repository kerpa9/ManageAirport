package airportmanage.airport.Services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import airportmanage.airport.Domain.DTOs.PassengerDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.PassengerRepository;
import airportmanage.airport.Repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepositroy;

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
                    .map(t -> new Tickets(null, null, t.getId_ticket(), t.getType_class(), t.getPrice(),
                            t.getSeat_number(), t.getCreated_at(), t.getActive(), t.getUser(), t.getPassenger()))
                    .collect(Collectors.toList()));

        }

        if (passengerDTO.bookings() != null) {
            passenger.setBookings(passengerDTO.bookings().stream()
                    .map(b -> new Booking(null, null, b.getId_booking(), null, null, null, null, null, b.getUser(),
                            b.getPassenger()))
                    .collect(Collectors.toList()));

        }

        return passengerRepositroy.savePassenger(passenger);

    }

    @Transactional(readOnly = true)
    public Page<Passenger> getAllPassengers(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }

        Long userLogin = filterLoginService.getUserLogin();
        if (userLogin == null) {
            throw new SecurityException("No authenticated user found");
        }

        return passengerRepositroy.findAllActivePassengers(userLogin, pageable);
    }

    @Transactional
    public Optional<Passenger> getOneById(Long id) {
        return passengerRepositroy.findActivePassengerById(id, filterLoginService.getUserLogin());
    }
}
