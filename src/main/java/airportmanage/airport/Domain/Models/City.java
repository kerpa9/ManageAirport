package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private LocalDateTime created_at;
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
