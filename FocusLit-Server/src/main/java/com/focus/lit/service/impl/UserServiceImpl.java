package com.focus.lit.service.impl;

import com.focus.lit.dto.UpdateUserInfoDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.UserAnalyticsService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAnalyticsService userAnalyticsService;

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
        Optional<User> user =userRepository.findById(updateUserInfoDto.getId());
        if (user.isPresent()) {
            user.get().setName(updateUserInfoDto.getUsername());
            user.get().setMail(updateUserInfoDto.getEmail());
            return userRepository.save(user.get());
        }else{
            throw new Exception("User is not found or Id is null");
        }
    }
}