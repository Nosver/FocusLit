package com.focus.lit.service.impl;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.MessageResponse;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.Mail;
import com.focus.lit.model.Token;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.model.enums.Role;
import com.focus.lit.repository.TokenRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.MailSenderService;
import com.focus.lit.service.UserAnalyticsService;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

// ecustomer@gmail.com1231231
// 5gsn7P)sBn
@Service
public class AuthenticationServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final TokenRepository tokenRepository;
    private final UserAnalyticsService userAnalyticsService;
    private final AuthenticationManager authenticationManager;
    private final MailSenderService mailSenderService;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtServiceImpl jwtService,
                                     TokenRepository tokenRepository,
                                     AuthenticationManager authenticationManager,
                                     UserAnalyticsService userAnalyticsService,
                                     MailSenderService mailSenderService
                                 ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.userAnalyticsService = userAnalyticsService;
        this.mailSenderService = mailSenderService;
    }

    public MessageResponse register(UserDto userDto) throws MessagingException {

        // check if all fields are filled
        if(!StringUtils.hasText(userDto.getMail()) || !StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getName())){
            return new MessageResponse("All fields must be filled");
        }

        // check if user already exist. if exist than authenticate the user
        if (userRepository.findByMail(userDto.getMail()).isPresent()) {
            return new MessageResponse("User with this email already exists");
        }

        User user = new User();
        user.setMail(userDto.getMail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        UserAnalytics userAnalytics = userAnalyticsService.createUserAnalytics();
        user.setUserAnalytics(userAnalytics);
        user.setEmailVerificationLink(UUID.randomUUID().toString());
        user.setIsAccountEnabled(false);
        userRepository.save(user);

        String subject = "Verify Your Email for Cafe-In Registration";
        String body = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2>Hi " + user.getName() + ",</h2>" +
                "<p>We just need to verify your email address before you register to FocusLit.</p>" +
                "<p><strong>Verify your email address:</strong> <a href='https://focuslit.koyeb.app/verify-email?token="+user.getEmailVerificationLink()+"'>Click here</a></p>" +
                "<p>Thanks!<br/>– FocusLit</p>" +
                "</body>" +
                "</html>";

        mailSenderService.sendNewMail(new Mail(user.getMail(), subject, body));
        return new MessageResponse("User registration was successful");
    }

    public AuthenticationResponse authenticate(UserDto userDto)  {

        // check if all fields are filled
        if(!StringUtils.hasText(userDto.getMail()) || !StringUtils.hasText(userDto.getPassword())){
            return new AuthenticationResponse(null, "All fields must be filled", null, -1);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getMail(),
                        userDto.getPassword()
                )
        );

        User user = userRepository.findByMail(userDto.getMail()).orElseThrow();

        /*if(!user.getIsAccountEnabled()){
            return new AuthenticationResponse(null, "Account is not verified!", null, -1);
        }*/

        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(userDto);
        saveUserToken(jwt,user);

        return new AuthenticationResponse(jwt, "User login was successful", user.getRole().toString(), user.getId());
    }
    
    private void revokeAllTokenByUser(UserDto userDto) {
        Optional<User> user = userRepository.findByMail(userDto.getMail());
        tokenRepository.deleteUserTokens(user.get().getId());
    }
    
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public boolean verifyEmail(String verificationCode){
        Optional<User> user = userRepository.findByEmailVerificationLink(verificationCode);
        if(user.isPresent()){
            user.get().setEmailVerificationLink(null);
            user.get().setIsAccountEnabled(true);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

}
