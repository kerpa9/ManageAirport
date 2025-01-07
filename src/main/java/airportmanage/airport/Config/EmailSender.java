package airportmanage.airport.Config;

import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import airportmanage.airport.Domain.Models.User;
import airportmanage.airport.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Value("${app.verification.baseUrl}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender mail;

    @Autowired
    private UserRepository userRepository;

    public void sendValidateEmail(User user) {
        logger.info("Send email: {}", user.getEmail());
        
        try {
            String token = UUID.randomUUID().toString();
            logger.debug("Generated token: {}", token);
            
            user.setVerification(token);
            user.setToken_expiry(LocalDateTime.now().plusDays(1));
            userRepository.save(user);
            logger.debug("Updated user");

            sendEmail(user.getEmail(), token);
            logger.info("Verified email");
            
        } catch (Exception e) {
            logger.error("Error send email: ", e);
            throw new RuntimeException("Error send email", e);
        }
    }

    private void sendEmail(String to, String token) {
        logger.debug("Building email for: {}", to);
        
        String verificationLink = baseUrl + "?token=" + token;
        logger.debug("Link verified: {}", verificationLink);

        MimeMessage message = mail.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject("Verify your account");
            helper.setText(buildEmailContent(verificationLink), true);
            
            logger.debug("Attempting to send email...");
            mail.send(message);
            logger.debug("Email sent successfully");
            
        } catch (MessagingException e) {
            logger.error("Error send email: ", e);
            throw new RuntimeException("Error send email : " + e.getMessage(), e);
        }
    }

    private String buildEmailContent(String verificationLink) {
        return """
                <html>
                <head>
                    <style>
                        .button {
                            background-color:rgb(175, 91, 76);
                            border: none;
                            color: white;
                            padding: 15px 32px;
                            text-align: center;
                            text-decoration: none;
                            display: inline-block;
                            font-size: 16px;
                            margin: 4px 2px;
                            cursor: pointer;
                            border-radius: 4px;
                        }
                        .container {
                            font-family: Arial, sans-serif;
                            max-width: 600px;
                            margin: 0 auto;
                            padding: 20px;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Account verification</h2>
                        <p>To verify your account, click on the following button: </p>
                        <a href='%s' class='button'>Verify account</a>
                        <p>If the button doesn't work, copy and paste this link into your browser:</p>
                        <p>%s</p>
                        <p>This link will expire in 24 hours.</p>
                    </div>
                </body>
                </html>
                """.formatted(verificationLink, verificationLink);
    }

    public boolean verifyEmail(String token) {
        logger.debug("Attempting to verify token: {}", token);
        
        return userRepository.findByVerification(token)
            .filter(user -> !user.getEmail_verified() && 
                          user.getToken_expiry().isAfter(LocalDateTime.now()))
            .map(user -> {
                user.verifyEmail();
                user.setActive(true);
                userRepository.save(user);
                logger.info("Email successfully verified for the user: {}", user.getEmail());
                return true;
            })
            .orElse(false);
    }
}