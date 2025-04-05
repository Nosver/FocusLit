package com.focus.lit.controller;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.mapper.UserMapper;
import com.focus.lit.model.User;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/get")
    public ResponseEntity<UserDto> get(@RequestParam Integer id) {
        try{
            Optional<User> user = userService.getById(id);
            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            UserDto userDto = userMapper.userToUserDto(user.get());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}