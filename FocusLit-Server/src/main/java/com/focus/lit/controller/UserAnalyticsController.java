package com.focus.lit.controller;


import com.focus.lit.dto.AddAchievementDto;
import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.WeeklyWorkDto;
import com.focus.lit.mapper.UserAnalyticsMapper;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.TokenRepository;
import com.focus.lit.service.UserAnalyticsService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/userAnalytics")
public class UserAnalyticsController {

    @Autowired
    private UserAnalyticsService userAnalyticsService;

    @Autowired
    private UserAnalyticsMapper userAnalyticsMapper;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public ResponseEntity<UserAnalyticsDto> get(int userId) {

        Optional<UserAnalytics> userAnalytics = userAnalyticsService.getUserAnalytics(userId);
        if(userAnalytics.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> u=userService.getById(userId);
        UserAnalyticsDto userAnalyticsDto = new UserAnalyticsDto(userId,userAnalytics.get().getScore(),userAnalytics.get().getStreak(),userAnalytics.get().getTotalWorkDuration(),userAnalytics.get().getUserRank(),u.get().getName());

        return new ResponseEntity<>(userAnalyticsDto, HttpStatus.OK);
    }

    @GetMapping("/getUserAchievements")
    public ResponseEntity<?> getUserAchievements(@RequestParam int userAnalyticsId) throws Exception {
        try{
            return new ResponseEntity<>(userAnalyticsService.getUserAchievements(userAnalyticsId), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addAchievement")
    public ResponseEntity<?> addAchievement(@RequestBody AddAchievementDto dto) {
        try {
            userAnalyticsService.addAchievement(dto);
            return ResponseEntity.ok("Achievement added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/update")
    public ResponseEntity<UserAnalyticsDto> update(@RequestBody UserAnalyticsDto userAnalyticsDto) {
        try{
            return new ResponseEntity<>(userAnalyticsService.update(userAnalyticsDto), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getWeeklyWork")
    public ResponseEntity<WeeklyWorkDto> getWeeklyWork(@RequestParam int userId) {
        return new ResponseEntity<>(userAnalyticsService.getWeeklyWork(userId), HttpStatus.OK);
    }
}
