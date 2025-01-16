package airportmanage.airport.Services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airportmanage.airport.Domain.DTOs.PlaneDTO;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Domain.Models.Plane;
import airportmanage.airport.Repository.PlaneRepository;
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

        return planeRepository.savePlane(plane);

    }

    @Transactional(readOnly = true)
    public Page<Plane> getAllPlane(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }

        Long userLogin = filterLoginService.getUserLogin();
        if (userLogin == null) {
            throw new SecurityException("No authenticated user found");
        }

        return planeRepository.findAllActive(userLogin, pageable);

    }

    @Transactional
    public Optional<Plane> getOnePlane(Long id) {
        return planeRepository.findActivePlaneById(id, filterLoginService.getUserLogin());
    }

}
