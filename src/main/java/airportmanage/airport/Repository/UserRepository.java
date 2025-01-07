package airportmanage.airport.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import airportmanage.airport.Domain.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByVerification(String token);

    Optional<User> findByEmail(String email);

}
