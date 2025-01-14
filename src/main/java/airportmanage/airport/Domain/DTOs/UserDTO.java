package airportmanage.airport.Domain.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import airportmanage.airport.Config.Security.HashPassword;
import airportmanage.airport.Domain.Enums.Genre;
import airportmanage.airport.Domain.Enums.RoleUser;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

        Long id,
        @NotBlank String full_name,
        @NotBlank @Email @Column(unique = true) String email,
        @NotBlank String password,
        @Enumerated(EnumType.STRING) @NotNull Genre genre,
        @NotNull @Enumerated(EnumType.STRING) @NotNull RoleUser role_user,
        String verification,
        LocalDateTime token_expiry,
        Boolean email_verified,
        Boolean active,
        List<PassengerDTO> passenger,
        List<TicketsDTO> tickets,
        List<BookingDTO> booking,
        LocalDateTime created_at

) {
    public UserDTO {
        HashPassword hash = new HashPassword();
        password = hash.hashingPass(password);
        active = active == null ? true : active;
        email_verified = email_verified == null ? false : email_verified;
        role_user = role_user == null ? RoleUser.user : role_user;
    }

}
