package airportmanage.airport.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.BookingDTO;
import airportmanage.airport.Domain.DTOs.PageableDTO;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Services.BookingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {

        try {
            Booking createBooking = bookingService.createBooking(bookingDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createBooking);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    @SuppressWarnings("rawtypes")
    @GetMapping
    private ResponseEntity<PageableDTO> getAllBookings(@PageableDefault(size = 5) Pageable pageable) {

        Page<Booking> booking = bookingService.getAllBooking(pageable);

        Page<BookingDTO> bookingDTO = booking.map(b -> new BookingDTO(b.getId_booking(), b.getBooking_date(),
                b.getNro_tickets(), b.getTotal_price(), b.getCreated_at(), b.getUserId(), b.getPassenger().getId(),
                b.getActive()));

        return ResponseEntity.ok(PageableDTO.fromPage(bookingDTO));

    }

    @GetMapping("/{id}")
    public Optional<Booking> getById(@PathVariable @Valid Long id) {
        return bookingService.getOneByID(id);
    }

}
