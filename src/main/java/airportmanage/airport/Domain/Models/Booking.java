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

@Entity(name = "Booking")
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Booking implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_login;
    private Long id_booking;
    private LocalDateTime booking_date;
    // private Long id_passenger;
    // private Long id_flight;
    private Integer nro_tickets;
    private Double total_price;
    private LocalDateTime created_at;
    // private Integer created_by;
    private Boolean active;

    public void setStatusInactiveBooking() {
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
