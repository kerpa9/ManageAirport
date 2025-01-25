package airportmanage.airport.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Config.EmailSender;
import airportmanage.airport.Domain.DTOs.Create.UserDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Repository.LoginRepository;
import airportmanage.airport.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmailSender emailSender;

    @Transactional
    public User createUser(@Valid UserDTO userDTO) {
        // Create user
        User user = new User();
        user.setFull_name(userDTO.full_name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setGenre(userDTO.genre());
        user.setRole_user(userDTO.role_user());
        user.setCreated_at(userDTO.created_at());
        user.setActive(userDTO.active());
        user.setEmail_verified(false);
        user = repository.save(user);

        if (userDTO.passenger() != null) {
            user.setPassengers(
                    userDTO.passenger().stream()
                            .map(u -> new Passenger(u.id_passenger(), null, null, u.first_name(), u.last_name(),
                                    u.nro_passport(),
                                    null,
                                    u.genre(),
                                    u.email(), null, u.phone(), null, null, u.ticket(), u.bookings()))
                            .collect(Collectors.toList()));

        }

        if (userDTO.tickets() != null) {
            user.setTickets(userDTO.tickets().stream().map(t -> new Tickets(t.id_ticket(), null, null, null, t.price(),
                    t.seat_number(), null, null, null, null)).collect(Collectors.toList()));

        }

        if (userDTO.booking() != null) {
            user.setBookings(userDTO.booking().stream().map(b -> new Booking(b.id_booking(), null, null, null,
                    b.nro_tickets(), b.total_price(), null, null,null, null, null)).collect(Collectors.toList()));
        }

        emailSender.sendValidateEmail(user);
        // Put in database login data

        Login login = new Login();
        login.setEmail(userDTO.email());
        login.setPassword(userDTO.password());
        login.setRole_user(userDTO.role_user());
        loginRepository.save(login);

        return user;
    }

}