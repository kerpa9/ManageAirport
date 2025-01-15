package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.TicketsDTO;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.PassengerRepositroy;
import airportmanage.airport.Repository.TicketsRepository;
import airportmanage.airport.Repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Tickets createTickets(@Valid TicketsDTO ticketsDTO) {
        
        Long loginId = filterLoginService.getUserLogin();
        
        var user = userRepository.findById(ticketsDTO.idUser()).get();
        
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
        tickets.setUser(user);;
        tickets.setActive(ticketsDTO.active());

        return ticketsRepository.save(tickets);

    }

}
