package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.BookingDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Repository.BookingRepository;
import airportmanage.airport.Repository.PassengerRepositroy;
import airportmanage.airport.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private PassengerRepositroy passengerRepositroy;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Booking createBooking(@Valid BookingDTO bookingDTO) {

        Long loginId = filterLoginService.getUserLogin();

        var user = userRepository.findById(bookingDTO.idUser()).get();

        var passenger = passengerRepositroy.findById(bookingDTO.idPassenger()).get();

        Booking booking = new Booking();

        Long seqBooking = bookingRepository.generatedInsertSequential(loginId) + 1;

        booking.setId_login(loginId);
        booking.setId_booking(seqBooking);
        booking.setBooking_date(bookingDTO.booking_date());
        booking.setNro_tickets(bookingDTO.nro_tickets());
        booking.setTotal_price(bookingDTO.total_price());
        booking.setCreated_at(bookingDTO.created_at());
        booking.setPassenger(passenger);
        booking.setUser(user);
        booking.setActive(bookingDTO.active());

        return bookingRepository.saveBookingWithRoles(booking);

    }

}
