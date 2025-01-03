package airportmanage.airport.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airportmanage.airport.Repository.UserRepository;

@Service
public class LoginService {
    @Autowired
    private UserRepository repository;

    

}
