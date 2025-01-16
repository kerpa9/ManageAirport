package airportmanage.airport.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Enums.RoleUser;
import airportmanage.airport.Domain.Models.Tickets;
import airportmanage.airport.Repository.BaseRepository.BaseRepository;

public interface TicketsRepository extends BaseRepository<Tickets> {

        default Tickets saveTicketsWithRoles(Tickets tickets) {
                return saveWithRoleValidation(tickets,
                                EnumSet.of(RoleUser.admin, RoleUser.receptionist));
        }

        @Override
        Optional<Tickets> findByIdAndIdLogin(Long id, Long id_login);

        @Override
        List<Tickets> findAllByIdLogin(Long id_login);

        @Query("SELECT COALESCE(MAX(t.id_ticket), 0) FROM Tickets t WHERE t.id_login = :id_login")
        Long generatedInsertSequential(@Param("id_login") Long id_login);

        @Query("""
                            select t from Tickets t where t.active = true and t.id_login=:id
                        """)
        Page<Tickets> findAllActive(Long id, Pageable pageable);

        @Query("""
                        select t from Tickets t
                        where
                        t.active=TRUE
                        and
                        t.id= :id
                        """)
        Tickets findByIdActive(@Param("id") Long id);

        @Query("""
                        select t from Tickets t where t.active=TRUE
                        and t.id_login = :id_login and t.id_ticket = :id_ticket""")
        Tickets findByIdUserLogin(
                        @Param("id_ticket") Long id_ticket,
                        @Param("id_login") Long id_login);

}
