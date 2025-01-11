package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.FlightDTO;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.FlightRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Transactional
    public Flight createFlight(@Valid FlightDTO flightDTO) {

        Flight flight = new Flight();

        Long loginId = filterLoginService.getUserLogin();

        Long seqFlight = flightRepository.generatedInsertSequential(loginId) + 1;

        flight.setId_login(loginId);
        flight.setId_flight(seqFlight);
        flight.setDeparture_time(flightDTO.departure_time());
        flight.setCheck_in(flightDTO.check_in());
        flight.setCreated_at(flightDTO.created_at());
        flight.setActive(flightDTO.active());

        return flightRepository.saveFlightWithRoles(flight);

    }

}
