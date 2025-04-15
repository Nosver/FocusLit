package com.focus.lit.controller;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.UpdateUserInfoDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.mapper.UserMapper;
import com.focus.lit.model.User;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

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

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try{
            if(!StringUtils.hasText(userDto.getMail()) || !StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getName())){
                return ResponseEntity.badRequest().body("All fields should be filled");
            }
            User createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserInfoDto updateUserInfoDto) {
        try {
            return ResponseEntity.ok(userService.updateUser(updateUserInfoDto));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}