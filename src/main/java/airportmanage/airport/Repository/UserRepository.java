package airportmanage.airport.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import airportmanage.airport.Domain.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
