package com.focus.lit.service;

import com.focus.lit.dto.ChangePasswordUserDto;
import com.focus.lit.dto.UpdateUserInfoDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getById(Integer id);

    User createUser(UserDto userDto) throws Exception;

    User updateUser(UpdateUserInfoDto updateUserInfoDto) throws Exception;

    ResponseEntity<?> changePassword(ChangePasswordUserDto changePasswordUserDto) throws Exception;

    Optional<UserAnalytics> getUserAnalytics(Integer userId);
}