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
import airportmanage.airport.Domain.DTOs.Create.CityDTO;
import airportmanage.airport.Domain.DTOs.Update.CityDTOU;
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

    @SuppressWarnings("rawtypes")
    @GetMapping
    private ResponseEntity<PageableDTO> getAllCities(@PageableDefault(size = 5) Pageable pageable) {

        try {
            Page<City> city = citiesService.getAllCity(pageable);

            Page<CityDTO> cityDTO = city.map(c -> new CityDTO(c.getId_city(), c.getName(), c.getCountry(), c.getLat(),
                    c.getLon(), c.getCreated_at(), c.getOrigin(), c.getDestination(), c.getActive()));

            return ResponseEntity.ok(PageableDTO.fromPage(cityDTO));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<City>> getById(@PathVariable @Valid Long id) {

        try {
            if (citiesService.getOneByID(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(citiesService.getOneByID(id));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Optional<City>>> softDelete(@PathVariable @Valid Long id) {

        try {

            if (citiesService.softDelete(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(citiesService.softDelete(id));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {

            throw new HandleException("ID doesn't exist" + e);

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<City>> update(@RequestBody @Valid CityDTOU cityDTOU, @PathVariable Long id) {

        try {

            Optional<City> update = citiesService.update(cityDTOU, id);

            if (citiesService.update(cityDTOU, id).isPresent()) {
                return ResponseEntity.status(HttpStatus.PROCESSING).body(update);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            throw new HandleException("ID doesn't exist" + e);

        }

    }

}
