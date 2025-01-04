package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Passenger")
@Table(name = "passenger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Passenger implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long id_login;
    private Long id_passenger;
    // @NotBlank
    private String first_name;
    // @NotBlank
    private String last_name;
    private LocalDateTime born_date;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    // @NotBlank
    // @Email
    private String email;
    // @NotBlank
    private String password;
    private String phone;
    private Boolean active;

    public void setStatusInactivePassenger() {
        this.active = false;

    }

    @Override
    public Long getUserId() {
        return id_login;
    }

    @Override
    public void setUserId(Long userId) {
        this.id_login = userId;
    }

}
