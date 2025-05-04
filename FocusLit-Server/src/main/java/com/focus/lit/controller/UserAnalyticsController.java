package com.focus.lit.controller;


import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.WeeklyWorkDto;
import com.focus.lit.mapper.UserAnalyticsMapper;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.service.UserAnalyticsService;
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

    @GetMapping("/get")
    public ResponseEntity<UserAnalyticsDto> get(int id) {
        Optional<UserAnalytics> userAnalytics = userAnalyticsService.getUserAnalytics(id);
        if(userAnalytics.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserAnalyticsDto userAnalyticsDto = userAnalyticsMapper.userAnalyticsToUserAnalyticsDto(userAnalytics.get());
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
