package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Flight")
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Flight implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_login;
    private Long id_flight;
    // private Long origin_id;
    // private Long destination_id;
    // private Long plane_id;
    private LocalDateTime departure_time;
    private LocalDateTime check_in;
    private LocalDateTime created_at;
    private Boolean active;

    public void setStatusInactiveFlight() {
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
