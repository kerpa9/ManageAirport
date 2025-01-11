package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Booking;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface BookingRepository extends BaseRepository<Booking> {


        default Booking saveBookingWithRoles(Booking booking) {
                return saveWithRoleValidation(booking,
                                EnumSet.of(RoleUser.admin, RoleUser.receptionist));
        }


        @Override
        Optional<Booking> findByIdAndIdLogin(Long id, Long id_login);

        @Override
        List<Booking> findAllByIdLogin(Long id_login);

        @Query("SELECT COALESCE(MAX(b.id_booking), 0) FROM Booking b WHERE b.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);

        @Query("""
                            select b from Booking b where b.active = true and b.id_login=:id
                        """)
        Page<Booking> findAllActive(Long id, Pageable pageable);

        @Query("""
                        select b from Booking b
                        where
                        b.active=TRUE
                        and
                        b.id= :id
                        """)
        Booking findByIdActive(@Param("id") Long id);

        @Query("""
                        select b from Booking b where b.active=TRUE
                        and b.id_login = :id_login and b.id_booking = :id_booking""")
        Booking findByIdUserLogin(
                        @Param("id_booking") Long id_booking,
                        @Param("id_login") Long id_login);

}
