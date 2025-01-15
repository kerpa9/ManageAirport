package airportmanage.airport.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.CityDTO;
import airportmanage.airport.Domain.Models.City;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.CitiesRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CitiesService {

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Transactional
    public City createCity(@Valid CityDTO cityDTO) {

        City city = new City();

        Long loginId = filterLoginService.getUserLogin();

        Long seqCity = citiesRepository.generatedInsertSequential(loginId) + 1;

        city.setId_login(loginId);
        city.setId_city(seqCity);
        city.setName(cityDTO.name());
        city.setCountry(cityDTO.country());
        city.setLat(cityDTO.lat());
        city.setLon(cityDTO.lon());
        city.setCreated_at(cityDTO.created_at());
        city.setActive(cityDTO.active());

        city.setId_login(loginId);
        city.setId_city(seqCity);
        city.setName(cityDTO.name());
        city.setCountry(cityDTO.country());
        city.setLat(cityDTO.lat());
        city.setLon(cityDTO.lon());
        city.setCreated_at(cityDTO.created_at());
        city.setActive(cityDTO.active());
    
        if (cityDTO.destination() != null) {
            city.setDestination(cityDTO.destination().stream()
                    .map(f -> new Flight(loginId, loginId, seqCity, null, null, null, null, city, null, null))
                    .collect(Collectors.toList()));
        }
    
        if (cityDTO.origin() != null) {
            city.setOrigin(cityDTO.origin().stream()
                    .map(f -> new Flight(loginId, loginId, seqCity, null, null, null, null, null, city, null))
                    .collect(Collectors.toList()));
        }

        return citiesRepository.saveCityWithRoles(city);

    }

}
