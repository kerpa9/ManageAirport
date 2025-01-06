package airportmanage.airport.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface PassengerRepositroy extends BaseRepository<Passenger> {

    @Override
    Optional<Passenger> findByIdAndIdLogin(Long id, Long id_login);

    @Override
    List<Passenger> findAllByIdLogin(Long id_login);

    @Query("SELECT COALESCE(MAX(p.id_passenger), 0) FROM Passenger p WHERE p.id_login = :id_login")
    Long generatedInsertSequential(@Param("id_login") Long id_login);

    @Query("""
                select p from Passenger p where p.active = true and p.id_login=:id
            """)
    Page<Passenger> findAllActive(Long id, Pageable pageable);

    @Query("""
            select p from Passenger p
            where
            p.active=TRUE
            and
            p.id= :id
            """)
    Passenger findByIdActive(@Param("id") Long id);

    @Query("""
            select p from Passenger p where p.active=TRUE
            and p.id_login = :id_login and p.id_passenger = :id_passenger""")
    Passenger findByIdUserLogin(
            @Param("id_passenger") Long id_passenger,
            @Param("id_login") Long id_login);

}
