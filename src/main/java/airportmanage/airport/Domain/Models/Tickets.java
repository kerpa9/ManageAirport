package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.TypeClass;
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

@Entity(name = "Tickets")
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Tickets implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long id_login;
    private Long id_ticket;
    @Enumerated(EnumType.STRING)
    private TypeClass type_class;
    private Double price;
    private Integer seat_number;
    
    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created_at;
    
    private Boolean active;
    @JsonBackReference("user-tickets")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference("passenger-tickets")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public Optional<Tickets> setStatusInactiveTicket() {
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
