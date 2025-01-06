package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.CityDTO;
import airportmanage.airport.Domain.Models.City;
import airportmanage.airport.Services.CitiesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/city")

public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    @PostMapping
    public ResponseEntity<City> registerCity(@RequestBody @Valid CityDTO cityDTO) {

        try {

            City createCity = citiesService.createCity(cityDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createCity);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

}
