package airportmanage.airport.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import airportmanage.airport.Domain.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // @Query("SELECT COALESCE(MAX(u.id), 0)  FROM User u")
    // Long getNextUserId();

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long getNextUserId(@Param("email") String email);

    @Query("SELECT u.email_verified FROM User u WHERE u.id = :userId")
    Boolean findEmailVerifiedById(@Param("userId") Long userId);

    Optional<User> findByVerification(String token);

    Optional<User> findByEmail(String email);

}
