package airportmanage.airport.Repository.BaseRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;

@NoRepositoryBean
public interface BaseRepository<T extends IUserOwnedEntity> extends JpaRepository<T, Long> {
    
    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    List<T> findAllByIdLogin(@Param("id_login") Long id_login);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id_login = :id_login")
    Page<T> findAllByIdLoginPaginated(@Param("id_login") Long id_login, Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.id_login = :id_login")
    Optional<T> findByIdAndIdLogin(@Param("id") Long id, @Param("id_login") Long id_login);

    @Query("SELECT COUNT(e) > 0 FROM #{#entityName} e WHERE e.id = :id AND e.id_login = :id_login")
    boolean existsByIdAndIdLogin(@Param("id") Long id, @Param("id_login") Long id_login);

}
