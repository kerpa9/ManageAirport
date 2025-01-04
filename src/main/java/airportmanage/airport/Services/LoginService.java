package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Repository.LoginRepository;


@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return loginRepository.findByEmail(email);
    }

    // @Autowired
    // public Login logged(){

    // }

}
