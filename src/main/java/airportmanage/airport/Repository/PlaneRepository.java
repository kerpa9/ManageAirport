package airportmanage.airport.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Domain.Models.Plane;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface PlaneRepository extends BaseRepository<Plane> {
    @Override
    Optional<Plane> findByIdAndIdLogin(Long id, Long id_login);

    @Override
    List<Plane> findAllByIdLogin(Long id_login);

    @Query("SELECT COALESCE(MAX(p.id_plane), 0) FROM Plane p WHERE p.id_login = :id_login")
    Long generatedInsertSequential(@Param("id_login") Long id_login);

    @Query("""
                select p from Plane p where p.active = true and p.id_login=:id
            """)
    Page<Plane> findAllActive(Long id, Pageable pageable);

    @Query("""
            select p from Plane p
            where
            p.active=TRUE
            and
            p.id= :id
            """)
    Plane findByIdActive(@Param("id") Long id);

    @Query("""
            select r from Plane r where r.active=TRUE
            and r.id_login = :id_login and r.id_plane = :id_plane""")
    Passenger findByIdUserLogin(
            @Param("id_plane") Long id_plane,
            @Param("id_login") Long id_login);

}
