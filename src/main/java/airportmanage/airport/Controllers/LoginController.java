package airportmanage.airport.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import airportmanage.airport.Domain.DTOs.DataJWTToken;
import airportmanage.airport.Domain.DTOs.LoginDTO;
import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/login")
@SuppressWarnings("rawtypes")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid LoginDTO loginDTO) {

        try {
            Authentication token = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());

            Authentication loginAuth = authenticationManager.authenticate(token);

            String jwtToken = tokenService.generatedToken((Login) loginAuth.getPrincipal());

            return ResponseEntity.ok(new DataJWTToken(jwtToken));

        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found in the database, please verify the registration");

        }
    }

    @PostMapping("/logged")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        tokenService.invalidateToken(token);
        return ResponseEntity.ok().body("Logged out successfully");

    }

}
