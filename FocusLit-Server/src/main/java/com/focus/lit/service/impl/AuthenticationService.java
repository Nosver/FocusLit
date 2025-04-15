package com.focus.lit.service.impl;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.model.Token;
import com.focus.lit.model.User;
import com.focus.lit.model.enums.Role;
import com.focus.lit.repository.TokenRepository;
import com.focus.lit.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// ecustomer@gmail.com1231231
// 5gsn7P)sBn
@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager

                                 ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;

    }

    public AuthenticationResponse register(User request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByMail(request.getMail()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist",null);
        }

        User user = new User();
        user.setMail(request.getMail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = repository.save(user);

       String jwt = jwtService.generateToken(user);
        

    
        saveUserToken(jwt, user);

       return new AuthenticationResponse(null, "User registration was successful",user.getRole().toString());

    }

    public AuthenticationResponse authenticate(User request) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByMail(request.getUsername()).orElseThrow();

        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful",user.getRole().toString());

    }
    
    private void revokeAllTokenByUser(User user) {

        tokenRepository.deleteUserTokens((long) user.getId());

    }
    
    private void saveUserToken(String jwt, User user ) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public AuthenticationResponse registerWithGoogle(User request) {
        if(repository.findByMail(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist",null);
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
        user = repository.save(user);



        return new AuthenticationResponse(null, "User registration was successful",user.getRole().toString());

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
