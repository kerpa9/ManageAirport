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
import airportmanage.airport.Domain.DTOs.Create.TicketsDTO;
import airportmanage.airport.Domain.DTOs.Update.TicketsDTOU;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Services.TicketsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    @PostMapping
    public ResponseEntity<Tickets> registerTickets(@RequestBody @Valid TicketsDTO ticketsDTO) {

        try {
            Tickets createTicket = ticketsService.createTickets(ticketsDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createTicket);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    @GetMapping
    @SuppressWarnings("rawtypes")
    private ResponseEntity<PageableDTO> getAllTickets(@PageableDefault(size = 5) Pageable pageable) {

        try {

            Page<Tickets> tickets = ticketsService.getAllTikets(pageable);

            Page<TicketsDTO> ticketsDTO = tickets.map(t -> new TicketsDTO(t.getId_ticket(), t.getType_class(),
                    t.getPrice(),
                    t.getSeat_number(), t.getUserId(), t.getPassenger().getId(), t.getCreated_at(), t.getActive()));

            return ResponseEntity.ok(PageableDTO.fromPage(ticketsDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tickets>> getOneTicketById(@PathVariable @Valid Long id) {

        try {
            if (ticketsService.getOneTicket(id).isPresent()) {

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketsService.getOneTicket(id));

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Tickets>> updateTicket(@RequestBody @Valid TicketsDTOU ticketsDTOU,
            @PathVariable @Valid Long id) {

        try {

            Optional<Tickets> update = ticketsService.updateTicket(ticketsDTOU, id);

            if (update.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(update);

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {

            throw new HandleException("ID doesn't exist" + e);

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Optional<Tickets>>> softDelete(@PathVariable @Valid Long id) {

        try {
            if (ticketsService.softDelete(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketsService.softDelete(id));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {

            throw new HandleException("ID doesn't exist" + e);

        }
    }

}
