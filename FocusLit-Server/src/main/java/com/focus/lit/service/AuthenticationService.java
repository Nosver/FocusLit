package com.focus.lit.service;

import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;

import javax.naming.AuthenticationException;

public interface AuthenticationService {
    AuthenticationResponse register(UserDto userDto);
    AuthenticationResponse authenticate(UserDto userDto) throws AuthenticationException;
    void revokeAllTokenByUser(UserDto userDto);
    void saveUserToken(String jwt, UserDto userDto);
    AuthenticationResponse registerWithGoogle(UserDto userDto);

}
