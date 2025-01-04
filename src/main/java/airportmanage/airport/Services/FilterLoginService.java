package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.Models.Login;
import airportmanage.airport.Repository.LoginRepository;

@Service
public class FilterLoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Long getUserLogin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Login user = loginRepository.findLoginByEmail(auth.getName());

        return user.getId();

    }

}
