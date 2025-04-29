package com.focus.lit.service.impl;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.ChangePasswordUserDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.Token;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.model.enums.Role;
import com.focus.lit.repository.TokenRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.UserAnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Optional;

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

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtServiceImpl jwtService,
                                     TokenRepository tokenRepository,
                                     AuthenticationManager authenticationManager,
                                     UserAnalyticsService userAnalyticsService
                                 ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.userAnalyticsService = userAnalyticsService;
    }

    public AuthenticationResponse register(UserDto userDto)  {

        // check if all fields are filled
        if(!StringUtils.hasText(userDto.getMail()) || !StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getName())){
            return new AuthenticationResponse(null, "All fields must be filled", null, -1);
        }

        // check if user already exist. if exist than authenticate the user
        if (userRepository.findByMail(userDto.getMail()).isPresent()) {
            return new AuthenticationResponse(null, "User with this email already exists", null, -1);
        }

        User user = new User();
        user.setMail(userDto.getMail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        UserAnalytics userAnalytics = userAnalyticsService.createUserAnalytics();
        user.setUserAnalytics(userAnalytics);
        user = userRepository.save(user);

       String jwt = jwtService.generateToken(user);
       saveUserToken(jwt, user);

       // with current logic we must send the token
       return new AuthenticationResponse(jwt, "User registration was successful",user.getRole().toString(), user.getId());
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
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(userDto);
        saveUserToken(jwt, user);

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

    public AuthenticationResponse registerWithGoogle(UserDto userDto) {
        if(userRepository.findByMail(userDto.getMail()).isPresent()) {
            return new AuthenticationResponse(null, "",null, -1);
        }
        User user = new User();
//        user.setFullName(request.getFullName());
//        user.setProvider(Provider.GOOGLE);
//        user.setEmail(request.getEmail());
//        String dummyPass="111111";
//        user.setPassword(passwordEncoder.encode(dummyPass));
//        user.setRole(Role.CUSTOMER);
//        user.setAvatar(request.getAvatar());
//        user.setLastLogin(Timestamp.from(Instant.now()));
//        user.setIsAccountEnabled(true);
        user = userRepository.save(user);

        return new AuthenticationResponse(null, "User registration was successful",user.getRole().toString(), user.getId());
    }



//    public AuthenticationResponse loginWithGoogle(User request) {
//        if(repository.findByEmail(request.getUsername()).isEmpty()) {
//            User user = new User();
//            user.setFullName(request.getFullName());
//            user.setProvider(Provider.GOOGLE);
//            user.setEmail(request.getEmail());
//            String dummyPass="111111";
//            user.setPassword(passwordEncoder.encode(dummyPass));
//            user.setRole(Role.CUSTOMER);
//            user.setAvatar(request.getAvatar());
//            user.setLastLogin(Timestamp.from(Instant.now()));
//            user.setIsAccountEnabled(true);
//            user = repository.save(user);
//
//            // Create empty cart for given customer id
//            cartService.createCartById(user.getId());
//        }
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),"111111"
//                )
//        );
//
//        User user = repository.findByEmail(request.getUsername()).orElseThrow();
//        String jwt = jwtService.generateToken(user);
//
//        revokeAllTokenByUser(user);
//        saveUserToken(jwt, user);
//
//        return new AuthenticationResponse(jwt, "User login was successful",user.getRole().toString());
//    }

//    public boolean verifyEmail(String verificationCode){
//        Optional<User> user =repository.findByEmailVerificationLink(verificationCode);
//        if(user.isPresent()){
//            user.get().setIsAccountEnabled(true);
//            user.get().setEmailVerificationLink(null);
//            repository.save(user.get());
//            return true;
//        }
//        return false;
//    }

}
