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
import airportmanage.airport.Domain.DTOs.Create.PlaneDTO;
import airportmanage.airport.Domain.DTOs.Update.PlaneDTOU;
import airportmanage.airport.Domain.Models.Plane;
import airportmanage.airport.Services.PlaneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/plane")
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    @PostMapping
    public ResponseEntity<Plane> registerPlane(@RequestBody @Valid PlaneDTO planeDTO) {

        try {

            Plane createPlane = planeService.createPlane(planeDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createPlane);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
    }

    @SuppressWarnings("rawtypes")
    @GetMapping
    private ResponseEntity<PageableDTO> getAllPlane(@PageableDefault(size = 5) Pageable pageable) {

        try {
            Page<Plane> plane = planeService.getAllPlane(pageable);

            Page<PlaneDTO> planeDTO = plane.map(p -> new PlaneDTO(p.getPlane_number(), p.getModel(),
                    p.getMax_capacity(), p.getFlight(), p.getAirline(), p.getCreated_at(), p.getActive()));

            return ResponseEntity.ok(PageableDTO.fromPage(planeDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Plane>> getById(@PathVariable @Valid Long id) {
        try {

            if (planeService.getOnePlane(id).isPresent()) {

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(planeService.getOnePlane(id));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Plane>> update(@RequestBody @Valid PlaneDTOU planeDTOU,
            @PathVariable @Valid Long id) {

        try {

            Optional<Plane> update = planeService.updatePlane(planeDTOU, id);

            if (update.isPresent()) {

                return ResponseEntity.status(HttpStatus.OK).body(update);

            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist: " + e);

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Optional<Plane>>> softDelete(@PathVariable @Valid Long id) {

        try {
            if (planeService.softDelete(id).isPresent()) {

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(planeService.softDelete(id));

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

}
