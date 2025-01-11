package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface FlightRepository extends BaseRepository<Flight> {

        default Flight saveFlightWithRoles(Flight flight) {
                return saveWithRoleValidation(flight, EnumSet.of(RoleUser.admin, RoleUser.receptionist));
        }

        @Override
        Optional<Flight> findByIdAndIdLogin(Long id, Long id_login);

        @Override
        List<Flight> findAllByIdLogin(Long id_login);

        @Query("SELECT COALESCE(MAX(f.id_flight), 0) FROM Flight f WHERE f.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);

        @Query("""
                            select f from Flight f where f.active = true and f.id_login=:id
                        """)
        Page<Flight> findAllActive(Long id, Pageable pageable);

        @Query("""
                        select f from Flight f
                        where
                        f.active=TRUE
                        and
                        f.id= :id
                        """)
        Flight findByIdActive(@Param("id") Long id);

        @Query("""
                        select f from Flight f where f.active=TRUE
                        and f.id_login = :id_login and f.id_flight = :id_flight""")
        Flight findByIdUserLogin(
                        @Param("id_flight") Long id_flight,
                        @Param("id_login") Long id_login);

}
