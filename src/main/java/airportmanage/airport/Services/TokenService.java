package airportmanage.airport.Services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import airportmanage.airport.Domain.Models.Login;

@Service
public class TokenService {

    @Value("${spring.security.secret}")
    private String apiSecret;
    
    private Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

    public String generatedToken(Login registerUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("airport")
                    .withSubject(registerUser.getEmail())
                    .withClaim("id", registerUser.getId())
                    .withExpiresAt(generateExpiration())
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating token", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token is null");
        }
        
        if (isTokenInvalidated(token)) {
            throw new RuntimeException("Token has been invalidated");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("airport")
                    .build()
                    .verify(token);
                    
            if (verifier.getSubject() == null) {
                throw new RuntimeException("Invalid token: subject is null");
            }
            
            return verifier.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid token", exception);
        }
    }

    public void invalidateToken(String token) {
        if (token != null && !token.isEmpty()) {
            String cleanToken = token.replace("Bearer ", "");
            invalidatedTokens.add(cleanToken);
        }
    }
    
    public boolean isTokenInvalidated(String token) {
        if (token == null || token.isEmpty()) {
            return true;
        }
        String cleanToken = token.replace("Bearer ", "");
        return invalidatedTokens.contains(cleanToken);
    }

    private Instant generateExpiration() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-05:00"));
    }
}