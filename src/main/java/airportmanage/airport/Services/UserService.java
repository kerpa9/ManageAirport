package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Config.EmailSender;
import airportmanage.airport.Domain.DTOs.UserDTO;
import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Repository.LoginRepository;
import airportmanage.airport.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmailSender emailSender;


    @Transactional
    public User createUser(@Valid UserDTO userDTO) {
        // Crear el usuario
        User user = new User();
        user.setFull_name(userDTO.full_name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setGenre(userDTO.genre());
        user.setRole_user(userDTO.role_user());
        user.setCreated_at(userDTO.created_at());
        user.setActive(userDTO.active());
        user.setEmail_verified(false);
        user = repository.save(user);
        emailSender.sendValidateEmail(user);
        
        Login login = new Login();
        login.setEmail(userDTO.email());
        login.setPassword(userDTO.password());
        login.setRole_user(userDTO.role_user());
        loginRepository.save(login);
    
        return user;
    }

}