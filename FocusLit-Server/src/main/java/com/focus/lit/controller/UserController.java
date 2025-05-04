package com.focus.lit.controller;

import com.focus.lit.dto.ChangePasswordUserDto;
import com.focus.lit.dto.UpdateUserInfoDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.mapper.UserMapper;
import com.focus.lit.model.User;
import com.focus.lit.service.TelegramService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TelegramService telegramService;

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

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordUserDto changePasswordUserDto) {
        try {
            return userService.changePassword(changePasswordUserDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while changing password. " + e.getMessage());
        }
    }

    @GetMapping("/getTelegramInviteLink")
    public ResponseEntity<?> getTelegramInviteLink(@RequestParam Integer userId) {
        return ResponseEntity.ok(telegramService.generateInviteLink(userId));
    }

}