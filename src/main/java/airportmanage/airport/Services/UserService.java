package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.DTOs.UserDTO;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User createUser(@Valid UserDTO userDTO) {
        
        User user = new User();
        user.setFull_name(userDTO.full_name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setGenre(userDTO.genre());
        user.setRole_user(userDTO.role_user());
        user.setCreated_at(userDTO.created_at());
        user.setActive(userDTO.active());

        return repository.save(user);

    }

}
