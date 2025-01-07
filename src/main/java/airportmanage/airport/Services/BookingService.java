package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.BookingDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Repository.BookingRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Transactional
    public Booking createBooking(@Valid BookingDTO bookingDTO) {

        Long loginId = filterLoginService.getUserLogin();

        Booking booking = new Booking();

        Long seqBooking = bookingRepository.generatedInsertSequential(loginId) + 1;

        booking.setId_login(loginId);
        booking.setId_booking(seqBooking);
        booking.setBooking_date(bookingDTO.booking_date());
        booking.setNro_tickets(bookingDTO.nro_tickets());
        booking.setTotal_price(bookingDTO.total_price());
        booking.setCreated_at(bookingDTO.created_at());
        booking.setActive(bookingDTO.active());

        return bookingRepository.saveBookingWithRoles(booking);

    }

}
