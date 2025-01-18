package airportmanage.airport.Domain.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import airportmanage.airport.Domain.Enums.Genre;
import airportmanage.airport.Domain.Enums.RoleUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String full_name;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private RoleUser role_user;
    private LocalDateTime created_at;
    private Boolean active;
    // Verify email
    private String verification;
    private LocalDateTime token_expiry;
    private Boolean email_verified;

    @JsonManagedReference("user-passengers")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers = new ArrayList<>();

    @JsonManagedReference("user-tickets")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> tickets = new ArrayList<>();

    @JsonManagedReference("user-bookings")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public Optional<User> setStatusInactiveUser() {
        active = false;
        return Optional.empty();
    }

    public void verifyEmail() {
        this.email_verified = true;
    }

}
