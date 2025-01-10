package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Repository.LoginRepository;
import airportmanage.airport.Repository.UserRepository;

@Service
public class FilterLoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    public Long getUserLogin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Login user = loginRepository.findLoginByEmail(auth.getName());

        return user.getId();

    }

    public Long getUser() {
        User create = userRepository.getReferenceById(getUser());
        return create.getId();
    }

}
