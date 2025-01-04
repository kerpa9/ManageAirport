package airportmanage.airport.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import airportmanage.airport.Domain.Models.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    Login findLoginByEmail(String email);

}
