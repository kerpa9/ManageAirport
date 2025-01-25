package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Passenger;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface PassengerRepository extends BaseRepository<Passenger> {

    static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESC = EnumSet.of(RoleUser.admin, RoleUser.manager, RoleUser.receptionist);
    static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESR = EnumSet.of(RoleUser.admin, RoleUser.receptionist,
            RoleUser.manager, RoleUser.user);
    static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESU = EnumSet.of(RoleUser.admin, RoleUser.receptionist, RoleUser.manager);
    static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLESD = EnumSet.of(RoleUser.admin, RoleUser.receptionist, RoleUser.manager);

    @Override
    default Passenger createDummyEntity(Long loginId) {
        Passenger dummy = new Passenger();
        dummy.setId_login(loginId);
        return dummy;
    }

    default Passenger savePassenger(Passenger passenger) {
        return saveWithRoleValidation(passenger, DEFAULT_AUTHORIZED_ROLESC);
    }

    default Page<Passenger> findAllActivePassengers(Long loginId, Pageable pageable) {
        return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLESR,
                p -> findAllActive(loginId, pageable),
                "READ_ACTIVE");
    }

    default Optional<Passenger> findActivePassengerById(Long id, Long loginId) {
        return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLESR,
                p -> Optional.ofNullable(findByIdActive(id)),
                "READ_BY_ID");
    }
    default Optional<Passenger> findActivePassengerByIdUpdate(Long id, Long loginId) {
        return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLESU,
                p -> Optional.ofNullable(findByIdActive(id)),
                "READ_BY_ID");
    }
    default Optional<Passenger> findActivePassengerByIdDelete(Long id, Long loginId) {
        return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLESD,
                p -> Optional.ofNullable(findByIdActive(id)),
                "READ_BY_ID");
    }

    @Query("""
                SELECT p FROM Passenger p
                WHERE p.active = true
                AND p.id_login = :id
                ORDER BY p.id_passenger
            """)
    Page<Passenger> findAllActive(@Param("id") Long id, Pageable pageable);

    @Query("""
                SELECT p FROM Passenger p
                WHERE p.active = true
                AND p.id = :id
            """)
    Passenger findByIdActive(@Param("id") Long id);

    @Query("SELECT COALESCE(MAX(p.id_passenger), 0) FROM Passenger p WHERE p.id_login = :id_login")
    Long generatedInsertSequential(@Param("id_login") Long id_login);
}
