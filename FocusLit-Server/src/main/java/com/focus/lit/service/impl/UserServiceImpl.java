package com.focus.lit.service.impl;

import com.focus.lit.model.User;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }
}