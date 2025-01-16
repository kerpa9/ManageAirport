package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface BookingRepository extends BaseRepository<Booking> {
    
        static final Set<RoleUser> DEFAULT_AUTHORIZED_ROLES = 
            EnumSet.of(RoleUser.admin, RoleUser.receptionist);
    
        @Override
        default Booking createDummyEntity(Long loginId) {
            Booking dummy = new Booking();
            dummy.setId_login(loginId);
            return dummy;
        }
    
        default Booking saveBooking(Booking booking) {
            return saveWithRoleValidation(booking, DEFAULT_AUTHORIZED_ROLES);
        }
    
        default Page<Booking> findAllActivePassengers(Long loginId, Pageable pageable) {
            return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLES,
                p -> findAllActive(loginId, pageable),
                "READ_ACTIVE"
            );
        }
    
        default Optional<Booking> findActivePassengerById(Long id, Long loginId) {
            return genericValidateFunction(
                createDummyEntity(loginId),
                DEFAULT_AUTHORIZED_ROLES,
                p -> Optional.ofNullable(findByIdActive(id)),
                "READ_BY_ID"
            );
        }
    
        @Query("""
            SELECT p FROM Booking p
            WHERE p.active = true
            AND p.id_login = :id
            ORDER BY p.id_booking
        """)
        Page<Booking> findAllActive(@Param("id") Long id, Pageable pageable);
    
        @Query("""
            SELECT p FROM Booking p
            WHERE p.active = true
            AND p.id = :id
        """)
        Booking findByIdActive(@Param("id") Long id);
    
        @Query("SELECT COALESCE(MAX(p.id_booking), 0) FROM Booking p WHERE p.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);
    }