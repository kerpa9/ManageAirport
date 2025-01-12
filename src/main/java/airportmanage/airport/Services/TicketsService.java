package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.TicketsDTO;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.PassengerRepositroy;
import airportmanage.airport.Repository.TicketsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class TicketsService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private PassengerRepositroy passengerRepositroy;

    @Transactional
    public Tickets createTickets(@Valid TicketsDTO ticketsDTO) {
        Tickets tickets = new Tickets();

        Long loginId = filterLoginService.getUserLogin();

        var user = passengerRepositroy.findById(ticketsDTO.idPassenger()).get();

        Long seqTicket = ticketsRepository.generatedInsertSequential(loginId) + 1;

        tickets.setId_login(loginId);
        tickets.setId_ticket(seqTicket);
        tickets.setType_class(ticketsDTO.type_class());
        tickets.setPrice(ticketsDTO.price());
        tickets.setSeat_number(ticketsDTO.seat_number());
        tickets.setCreated_at(ticketsDTO.created_at());
        tickets.setPassenger(user);
        tickets.setActive(ticketsDTO.active());

        return ticketsRepository.save(tickets);

    }

}
