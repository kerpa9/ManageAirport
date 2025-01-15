package airportmanage.airport.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.PlaneDTO;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Domain.Models.Plane;
import airportmanage.airport.Repository.PlaneRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PlaneService {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Transactional
    public Plane createPlane(@Valid PlaneDTO planeDTO) {

        Plane plane = new Plane();

        Long loginId = filterLoginService.getUserLogin();

        Long seqPlane = planeRepository.generatedInsertSequential(loginId) + 1;

        plane.setId_login(loginId);
        plane.setId_plane(seqPlane);
        plane.setPlane_number(planeDTO.plane_number());
        plane.setModel(planeDTO.model());
        plane.setMax_capacity(planeDTO.max_capacity());
        plane.setAirline(planeDTO.airline());
        plane.setCreated_at(planeDTO.created_at());
        plane.setActive(planeDTO.active());

        if (planeDTO.flight() != null) {
            plane.setFlight(planeDTO.flight().stream()
                    .map(f -> new Flight(null, loginId, seqPlane, null, null, null, null, null, null, plane))
                    .collect(Collectors.toList()));
        }

        return planeRepository.savePlaneWithRole(plane);

    }

}
