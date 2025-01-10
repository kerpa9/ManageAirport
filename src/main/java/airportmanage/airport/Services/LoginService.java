package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import airportmanage.airport.Repository.LoginRepository;
import airportmanage.airport.Repository.UserRepository;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            if (!userRepository.findEmailVerifiedById(userRepository.getNextUserId())) {

                throw new RuntimeException("Error");

            }
            return loginRepository.findByEmail(email);

    

    }

}
