package airportmanage.airport.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airportmanage.airport.Config.HandleException.HandleException;
import airportmanage.airport.Domain.DTOs.Create.BookingDTO;
import airportmanage.airport.Domain.DTOs.Update.BookingDTOU;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Repository.BookingRepository;
import airportmanage.airport.Repository.PassengerRepository;
import airportmanage.airport.Repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private PassengerRepository passengerRepositroy;

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

        if (bookingDTO.nro_tickets() <= 5) {

            booking.setNro_tickets(bookingDTO.nro_tickets());

        } else {

            booking.setNro_tickets(0);
        }

        booking.setTotal_price(bookingDTO.total_price());
        booking.setCreated_at(bookingDTO.created_at());
        booking.setPassenger(passenger);
        booking.setUser(user);
        booking.setActive(bookingDTO.active());

        return bookingRepository.saveBooking(booking);

    }

    @Transactional(readOnly = true)
    public Page<Booking> getAllBooking(Pageable pageable) {
        if (pageable == null) {
            throw new HandleException("Pageable parameter cannot be null");
        }

        Long userLog = filterLoginService.getUserLogin();
        if (userLog == null) {
            throw new SecurityException("No authenticated user found");
        }

        return bookingRepository.findAllActiveBookings(userLog, pageable);
    }

    @Transactional
    public Optional<Booking> getOneByID(Long id) {

        return bookingRepository.findActiveBookingById(id, filterLoginService.getUserLogin());
    }

    @Transactional
    public Optional<Optional<Booking>> softDelete(Long id) {

        Optional<Booking> setSoftDelete = bookingRepository.findActiveBookingById(id,
                filterLoginService.getUserLogin());

        return setSoftDelete.map(booking -> {
            return booking.setStatusInactiveBooking();
        }).or(() -> Optional.empty());
    }

    @Transactional
    public Optional<Booking> updateBooking(@Valid BookingDTOU bookingDTOU, Long id) {
        Optional<Booking> updateBook = bookingRepository.findActiveBookingById(id, filterLoginService.getUserLogin());

        return updateBook.map(booking -> {
            booking.setNro_tickets(bookingDTOU.nro_tickets());
            booking.setTotal_price(bookingDTOU.total_price());
            booking.setBooking_status(bookingDTOU.booking_status());
            booking.setCreated_at(bookingDTOU.created_at());
            return bookingRepository.saveBooking(booking);
        });
    }

}
