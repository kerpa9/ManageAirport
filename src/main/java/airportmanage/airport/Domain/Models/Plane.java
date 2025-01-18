package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import airportmanage.airport.Domain.Enums.Airline;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Plane")
@Table(name = "plane")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Plane implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long id_login;
    private Long id_plane;
    private Integer plane_number;
    private String model;
    private Integer max_capacity;
    @Enumerated(EnumType.STRING)
    private Airline airline;
    private LocalDateTime created_at;
    private Boolean active;

    @JsonManagedReference("plane-flight")
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flight = new ArrayList<>();

    public Optional<Plane> setStatusInactivePlane() {
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
