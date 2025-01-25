package airportmanage.airport.Services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airportmanage.airport.Domain.DTOs.Create.PlaneDTO;
import airportmanage.airport.Domain.DTOs.Update.PlaneDTOU;
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
        plane.setPlane_status(planeDTO.plane_status());
        plane.setActive(planeDTO.active());

        if (planeDTO.flight() != null) {
            plane.setFlight(planeDTO.flight().stream()
                    .map(f -> new Flight(null, loginId, seqPlane, null, f.getCheck_in_start(), f.getCheck_in_end(),
                            null,
                            f.getCreated_at(), f.getFlight_status(), f.getActive(),
                            f.getOrigin(), f.getDestination(), f.getPlane()))
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

        return planeRepository.findAllActivePlane(userLogin, pageable);

    }

    @Transactional
    public Optional<Plane> getOnePlane(Long id) {
        return planeRepository.findActivePlaneById(id, filterLoginService.getUserLogin());
    }

    @Transactional
    public Optional<Plane> updatePlane(@Valid PlaneDTOU planedDTOU, Long id) {

        Optional<Plane> updatePlane = planeRepository.findActivePlaneById(id, filterLoginService.getUserLogin());

        return updatePlane.map(plane -> {
            plane.setPlane_number(planedDTOU.plane_number());
            plane.setModel(planedDTOU.model());
            plane.setMax_capacity(planedDTOU.max_capacity());
            plane.setAirline(planedDTOU.airline());
            plane.setPlane_status(planedDTOU.plane_status());
            plane.setCreated_at(planedDTOU.created_at());
            return planeRepository.savePlane(plane);
        });
    }

    @Transactional
    public Optional<Optional<Plane>> softDelete(Long id) {

        Optional<Plane> setSoftDelete = planeRepository.findActivePlaneById(id, filterLoginService.getUserLogin());

        return setSoftDelete.map(plane -> {
            return plane.setStatusInactivePlane();
        }).or(() -> Optional.empty());

    }

}
