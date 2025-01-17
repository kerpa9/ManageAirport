package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Flight;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface FlightRepository extends BaseRepository<Flight> {

        static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESC = EnumSet.of(RoleUser.admin, RoleUser.manager);
        static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESR = EnumSet.of(RoleUser.admin, RoleUser.receptionist,
                        RoleUser.manager, RoleUser.user, RoleUser.developers);
        static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESU = EnumSet.of(RoleUser.admin, RoleUser.manager, RoleUser.receptionist);
        static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESD = EnumSet.of(RoleUser.admin, RoleUser.manager);

        @Override
        default Flight createDummyEntity(Long loginId) {
                Flight dummy = new Flight();
                dummy.setId_login(loginId);
                return dummy;
        }

        default Flight saveFlight(Flight flight) {
                return saveWithRoleValidation(flight, DEFAULT_AUTHORIZED_ROLESC);
        }

        default Page<Flight> findAllActiveFlight(Long loginId, Pageable pageable) {
                return genericValidateFunction(
                                createDummyEntity(loginId),
                                DEFAULT_AUTHORIZED_ROLESR,
                                p -> findAllActive(loginId, pageable),
                                "READ_ACTIVE");
        }

        default Optional<Flight> findActiveFlightById(Long id, Long loginId) {
                return genericValidateFunction(
                                createDummyEntity(loginId),
                                DEFAULT_AUTHORIZED_ROLESR,
                                p -> Optional.ofNullable(findByIdActive(id)),
                                "READ_BY_ID");
        }

        @Query("""
                            SELECT p FROM Flight p
                            WHERE p.active = true
                            AND p.id_login = :id
                            ORDER BY p.id_flight
                        """)
        Page<Flight> findAllActive(@Param("id") Long id, Pageable pageable);

        @Query("""
                            SELECT p FROM Flight p
                            WHERE p.active = true
                            AND p.id = :id
                        """)
        Flight findByIdActive(@Param("id") Long id);

        @Query("SELECT COALESCE(MAX(p.id_flight), 0) FROM Flight p WHERE p.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);
}