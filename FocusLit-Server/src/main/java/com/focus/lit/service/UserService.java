package com.focus.lit.service;

import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getById(Integer id);

    User createUser(UserDto userDto);
}