package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Repository.LoginRepository;
import airportmanage.airport.Repository.UserRepository;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private FilterLoginService filterLoginService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var id = filterLoginService.getUserLogin();

        if (userRepository.findEmailVerifiedById(id)) {

            return loginRepository.findByEmail(email);

        } else {
            throw new RuntimeException("Error");
        }

    }

}
