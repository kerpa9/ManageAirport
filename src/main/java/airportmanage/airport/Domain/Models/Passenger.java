package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.Genre;
import jakarta.persistence.Entity;
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
    private Long id_login;
    private Long id_passenger;
    private String firs_name;
    private String last_name;
    private LocalDateTime born_date;
    private Genre genre;
    private String email;
    private String password;
    private String phone;
    private Boolean active;

    @Override
    public Long getUserId() {
        return id_login;
    }

    @Override
    public void setUserId(Long userId) {
        this.id_login = userId;
    }

}
