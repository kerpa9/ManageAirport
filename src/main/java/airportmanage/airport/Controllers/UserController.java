package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Config.EmailSender;
import airportmanage.airport.Domain.DTOs.Create.UserDTO;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {

        try {

            User createUser = userService.createUser(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam(required = true) String token) {
        try {
            boolean verified = emailSender.verifyEmail(token);
            if (verified) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", "/")
                        .build();
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/verification-failed")
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la verificación");
        }
    }


}
