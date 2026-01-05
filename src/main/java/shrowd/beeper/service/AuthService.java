package shrowd.beeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shrowd.beeper.config.AppUser;
import shrowd.beeper.config.jwt.UserAuthenticationProvider;
import shrowd.beeper.exception.BadRequestException;
import shrowd.beeper.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;


    public String getJwtToken(String email) {
        AppUser appUser = (AppUser) userService.loadUserByUsername(email);
        String userRole = appUser.getUser().getRole().name();
        String userEmail = appUser.getUser().getEmail();

        return userAuthenticationProvider.createToken(userEmail, userRole);
    }

    public void validateCredentials(String email, String password) {
        try {
            AppUser appUser = (AppUser) userService.loadUserByUsername(email);

            if (!new BCryptPasswordEncoder().matches(password, appUser.getPassword())) {
                throw new BadRequestException("Invalid credentials");
            }

        } catch (UsernameNotFoundException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public void registerUser(String firstName,
                             String lastName,
                             String email,
                             String password) {

        userService.registerUser(firstName, lastName, email, password);
    }
}