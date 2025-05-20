package com.focus.lit.service.impl;

import com.focus.lit.dto.*;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.UserAnalyticsService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAnalyticsService userAnalyticsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(UserDto userDto) throws Exception {
        Optional<User> existingUser = userRepository.findByMail(userDto.getMail());
        if (existingUser.isPresent()) {
            throw new Exception("User with this email already exists");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setMail(userDto.getMail());
        user.setCreatedAt(LocalDateTime.now());

        UserAnalytics userAnalytics = userAnalyticsService.createUserAnalytics();
        user.setUserAnalytics(userAnalytics);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserInfoDto updateUserInfoDto) throws Exception {
        Optional<User> userOpt = userRepository.findById(updateUserInfoDto.getId());
        if (userOpt.isEmpty()) {
            throw new Exception("User is not found or Id is null");
        }

        User user = userOpt.get();

        Optional<User> existingUser = userRepository.findByMail(updateUserInfoDto.getEmail());
        if (existingUser.isPresent() && existingUser.get().getId() != user.getId()) {
            throw new Exception("User with this email already exists");
        }

        user.setName(updateUserInfoDto.getUsername());
        user.setMail(updateUserInfoDto.getEmail());
        return userRepository.save(user);
    }

    public ResponseEntity<?> changePassword(ChangePasswordUserDto changePasswordUserDto) {
        User user = userRepository.findByMail(changePasswordUserDto.getMail()).orElseThrow();
        if(!passwordEncoder.matches(changePasswordUserDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Old password is incorrect"));
        }
        user.setPassword(passwordEncoder.encode(changePasswordUserDto.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public Optional<UserAnalytics> getUserAnalytics(Integer userId) {
        return Optional.ofNullable(userRepository.findById(userId).get().getUserAnalytics());
    }


}