package airportmanage.airport.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airportmanage.airport.Config.HandleException.HandleException;
import airportmanage.airport.Domain.DTOs.CityDTO;
import airportmanage.airport.Domain.Models.City;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.CitiesRepository;
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

    @Transactional(readOnly = true)
    public Page<City> getAllCity(Pageable pageable) {
        if (pageable == null) {
            throw new HandleException("Pageable parameter cannot be null");
        }

        Long userLogin = filterLoginService.getUserLogin();

        if (userLogin == null) {
            throw new SecurityException("No authenticated user found");
        }

        return citiesRepository.findAllActive(userLogin, pageable);
    }


    @Transactional
    public City getOneByID(Long id){
        return citiesRepository.findByIdUserLogin(id, filterLoginService.getUserLogin());

    }
}
