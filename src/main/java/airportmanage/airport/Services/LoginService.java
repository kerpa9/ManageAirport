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

        try {
            System.out.println("------------------------");
            System.out.println("------------------------");
            System.out.println(userRepository.getNextUserId());
            System.out.println(userRepository.findEmailVerifiedById(userRepository.getNextUserId()));
            System.out.println("------------------------");
            System.out.println("------------------------");
            if (!userRepository.findEmailVerifiedById(userRepository.getNextUserId())) {

                throw new RuntimeException("Error");

            }
            return loginRepository.findByEmail(email);

        } catch (Exception e) {
            throw new RuntimeException("Error");

        }

    }

}
