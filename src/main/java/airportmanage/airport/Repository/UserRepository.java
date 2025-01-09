package airportmanage.airport.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import airportmanage.airport.Domain.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean findEmailVerifiedById(Long userId);

    Optional<User> findByVerification(String token);

    Optional<User> findByEmail(String email);

}
