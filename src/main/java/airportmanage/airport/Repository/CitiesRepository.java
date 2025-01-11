package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.City;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface CitiesRepository extends BaseRepository<City> {

        default City saveCityWithRoles(City city) {
                return saveWithRoleValidation(city,
                                EnumSet.of(RoleUser.admin));
        }

        @Override
        Optional<City> findByIdAndIdLogin(Long id, Long id_login);

        @Override
        List<City> findAllByIdLogin(Long id_login);

        @Query("SELECT COALESCE(MAX(c.id_city), 0) FROM City c WHERE c.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);

        @Query("""
                            select c from City c where c.active = true and c.id_login=:id
                        """)
        Page<City> findAllActive(Long id, Pageable pageable);

        @Query("""
                        select c from City c
                        where
                        c.active=TRUE
                        and
                        c.id= :id
                        """)
        City findByIdActive(@Param("id") Long id);

        @Query("""
                        select c from City c where c.active=TRUE
                        and c.id_login = :id_login and c.id_city = :id_city""")
        City findByIdUserLogin(
                        @Param("id_city") Long id_city,
                        @Param("id_login") Long id_login);

}
