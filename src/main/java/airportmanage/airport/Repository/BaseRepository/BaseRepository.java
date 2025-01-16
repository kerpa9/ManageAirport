package airportmanage.airport.Repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Config.HandleException.HandleException;
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

    default <E extends IUserOwnedEntity, R> R genericValidateFunction(
            E entity, 
            Set<RoleUser> authorizedRoles,
            Function<E, R> action,
            String operationType) {
        
        RoleUser userRole = findLoginRole(entity.getId_login());
        
        if (!authorizedRoles.contains(userRole)) {
            throw new HandleException(
                String.format("User with role %s is not authorized to perform %s operation", 
                userRole, 
                operationType)
            );
        }
        
        return action.apply(entity);
    }

    default T saveWithRoleValidation(T entity, Set<RoleUser> authorizedRoles) {
        return genericValidateFunction(
            entity,
            authorizedRoles,
            this::save,
            "SAVE"
        );
    }

    default Optional<T> findByIdWithRoleValidation(Long id, Long loginId, Set<RoleUser> authorizedRoles) {
        T dummyEntity = createDummyEntity(loginId);
        return genericValidateFunction(
            dummyEntity,
            authorizedRoles,
            e -> findByIdAndIdLogin(id, loginId),
            "READ"
        );
    }

    default Page<T> findAllWithRoleValidation(Long loginId, Pageable pageable, Set<RoleUser> authorizedRoles) {
        T dummyEntity = createDummyEntity(loginId);
        return genericValidateFunction(
            dummyEntity,
            authorizedRoles,
            e -> findAllByIdLoginPaginated(loginId, pageable),
            "READ_ALL"
        );
    }

    T createDummyEntity(Long loginId);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    List<T> findAllByIdLogin(@Param("id_login") Long id_login);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    Page<T> findAllByIdLoginPaginated(@Param("id_login") Long id_login, Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.id_login = :id_login")
    Optional<T> findByIdAndIdLogin(@Param("id") Long id, @Param("id_login") Long id_login);
}