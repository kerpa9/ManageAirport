package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import airportmanage.airport.Config.RegisterFilterId.IUserOwnedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity(name = "City")
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class City implements IUserOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long id_login;
    private Long id_city;
    private String name;
    private String country;
    private Double lat;
    private Double lon;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    private Boolean active;

    @JsonManagedReference("city-origin-flights")
    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> origin = new ArrayList<>();

    @JsonManagedReference("city-destination-flights")
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> destination = new ArrayList<>();

    public Optional<City> setStatusInactiveCity() {
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
