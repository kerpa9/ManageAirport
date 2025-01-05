package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.PlaneDTO;
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

}
