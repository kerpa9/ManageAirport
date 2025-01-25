package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer nro_tickets;
    private Double total_price;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created_at;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus booking_status;
    private Boolean active;

    @JsonBackReference("user-bookings")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference("passenger-bookings")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public Optional<Booking> setStatusInactiveBooking() {
        active = false;
        return Optional.empty();

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
