package airportmanage.airport.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import airportmanage.airport.Domain.DTOs.Create.TicketsDTO;
import airportmanage.airport.Domain.DTOs.Update.TicketsDTOU;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.PassengerRepository;
import airportmanage.airport.Repository.TicketsRepository;
import airportmanage.airport.Repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class TicketsService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private PassengerRepository passengerRepositroy;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Tickets createTickets(@Valid TicketsDTO ticketsDTO) {

        Long loginId = filterLoginService.getUserLogin();

        var user = userRepository.findById(filterLoginService.getUserLogin()).get();

        var passenger = passengerRepositroy.findById(ticketsDTO.idPassenger()).get();

        Tickets tickets = new Tickets();

        Long seqTicket = ticketsRepository.generatedInsertSequential(loginId) + 1;

        tickets.setId_login(loginId);
        tickets.setId_ticket(seqTicket);
        tickets.setType_class(ticketsDTO.type_class());
        tickets.setPrice(ticketsDTO.price());
        tickets.setSeat_number(ticketsDTO.seat_number());
        tickets.setCreated_at(ticketsDTO.created_at());
        tickets.setPassenger(passenger);
        tickets.setUser(user);
        tickets.setActive(ticketsDTO.active());

        return ticketsRepository.saveTicket(tickets);

    }

    @Transactional(readOnly = true)
    public Page<Tickets> getAllTikets(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }

        Long userLogin = filterLoginService.getUserLogin();
        if (userLogin == null) {
            throw new SecurityException("No authenticated user found");
        }
        return ticketsRepository.findAllActiveTicket(userLogin, pageable);

    }

    @Transactional
    public Optional<Tickets> getOneTicket(Long id) {
        return ticketsRepository.findActiveTicketById(id, filterLoginService.getUserLogin());
    }

    @Transactional
    public Optional<Tickets> updateTicket(@Valid TicketsDTOU ticketDtou, Long id) {

        Optional<Tickets> updateTicket = ticketsRepository.findActiveTicketById(id, filterLoginService.getUserLogin());

        return updateTicket.map(ticket -> {

            ticket.setType_class(ticketDtou.type_class());
            ticket.setPrice(ticketDtou.price());
            ticket.setSeat_number(ticketDtou.seat_number());

            return ticketsRepository.saveTicket(ticket);
        });

    }

    @Transactional
    public Optional<Optional<Tickets>> softDelete(Long id) {
        Optional<Tickets> setSoftDelete = ticketsRepository.findActiveTicketById(id, filterLoginService.getUserLogin());

        return setSoftDelete.map(ticket -> {
            return ticket.setStatusInactiveTicket();
        }).or(() -> Optional.empty());
    }
}
