package airportmanage.airport.Config.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashingPass(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

}
