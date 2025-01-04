package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.UserDTO;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@SuppressWarnings("rawtypes")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {

        try {

            User createUser = userService.createUser(userDTO);

            userService.inserLogin(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @GetMapping
    public ResponseEntity response() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Respuestas");
    }

}
