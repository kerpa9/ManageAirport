package airportmanage.airport.Repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.RoleUser;

@NoRepositoryBean
public interface BaseRepository<T extends IUserOwnedEntity> extends JpaRepository<T, Long> {
    
        @Query("""
        SELECT l.role_user 
        FROM Login l 
        WHERE l.id = :loginId
        """)
    RoleUser findLoginRole(@Param("loginId") Long loginId);

    default T saveWithRoleValidation(T entity, Set<RoleUser> authorizedRoles) {
        RoleUser userRole = findLoginRole(entity.getId_login());
        
        if (authorizedRoles.contains(userRole)) {
            return save(entity);
        }
        throw new RuntimeException("No role authorization for role: " + userRole);
    }

    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    List<T> findAllByIdLogin(@Param("id_login") Long id_login);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    Page<T> findAllByIdLoginPaginated(@Param("id_login") Long id_login, Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.id_login = :id_login")
    Optional<T> findByIdAndIdLogin(@Param("id") Long id, @Param("id_login") Long id_login);

    @Query("SELECT COUNT(e) > 0 FROM #{#entityName} e WHERE e.id = :id AND e.id_login = :id_login")
    boolean existsByIdAndIdLogin(@Param("id") Long id, @Param("id_login") Long id_login);

}
