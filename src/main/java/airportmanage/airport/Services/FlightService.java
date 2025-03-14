package airportmanage.airport.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airportmanage.airport.Config.HandleException.HandleException;
import airportmanage.airport.Domain.DTOs.Create.FlightDTO;
import airportmanage.airport.Domain.DTOs.Update.FlightDTOU;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.CitiesRepository;
import airportmanage.airport.Repository.FlightRepository;
import airportmanage.airport.Repository.PlaneRepository;
import jakarta.validation.Valid;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private PlaneRepository planeRepository;

    @Transactional
    public Flight createFlight(@Valid FlightDTO flightDTO) {

        Flight flight = new Flight();

        Long loginId = filterLoginService.getUserLogin();

        var plane = planeRepository.findById(flightDTO.idPlane()).get();

        var origin = citiesRepository.findById(flightDTO.origin_id()).get();

        var destination = citiesRepository.findById(flightDTO.destination_id()).get();

        Long seqFlight = flightRepository.generatedInsertSequential(loginId) + 1;

        flight.setId_login(loginId);
        flight.setId_flight(seqFlight);
        flight.setDeparture_time(flightDTO.departure_time());
        flight.setCheck_in_start(flightDTO.check_in_start());
        flight.setCheck_in_end(flightDTO.check_in_end());
        flight.setAvailable_seats(flightDTO.available_seats());
        flight.setCreated_at(flightDTO.created_at());

        if (origin != destination) {
            flight.setOrigin(origin);
            flight.setDestination(destination);
        } else {
            throw new HandleException("Error, destination or origin not valid");
        }

        flight.setPlane(plane);
        flight.setFlight_status(flightDTO.flight_status());
        flight.setActive(flightDTO.active());

        return flightRepository.saveFlight(flight);

    }

    @Transactional(readOnly = true)
    public Page<Flight> getAllFlights(Pageable pageable) {

        if (pageable == null) {
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }

        Long userLogin = filterLoginService.getUserLogin();
        if (userLogin == null) {
            throw new SecurityException("No authenticated user found");
        }

        return flightRepository.findAllActiveFlight(userLogin, pageable);
    }

    @Transactional
    public Optional<Flight> getOneById(Long id) {
        return flightRepository.findActiveFlightById(id, filterLoginService.getUserLogin());
    }

    @Transactional
    public Optional<Optional<Flight>> softDelete(Long id) {

        Optional<Flight> setSoftDelete = flightRepository.findActiveFlightByIdDelete(id, filterLoginService.getUserLogin());

        return setSoftDelete.map(flight -> {
            return flight.setStatusInactiveFlight();
        }).or(() -> Optional.empty());
    }

    @Transactional
    public Optional<Flight> updateFlight(@Valid FlightDTOU flightDTOU, Long id) {

        Optional<Flight> updateFlight = flightRepository.findActiveFlightByIdUpdate(id, filterLoginService.getUserLogin());

        return updateFlight.map(flight -> {
            flight.setDeparture_time(flightDTOU.departure_time());
            flight.setCheck_in_start(flightDTOU.check_in_start());
            flight.setCheck_in_end(flightDTOU.check_in_end());
            flight.setAvailable_seats(flightDTOU.available_seats());
            flight.setFlight_status(flightDTOU.flight_status());
            return flightRepository.saveFlight(flight);

        });

    }

}
