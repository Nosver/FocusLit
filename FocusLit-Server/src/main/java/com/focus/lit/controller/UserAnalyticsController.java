package com.focus.lit.controller;


import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.mapper.UserAnalyticsMapper;
import com.focus.lit.model.User;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.service.UserAnalyticsService;
import com.focus.lit.service.impl.UserAnalyticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<?> getUserAchievemnts(@RequestParam int userAnalyticsId) throws Exception {
        try{
            return new ResponseEntity<>(userAnalyticsService.getUserAchievements(userAnalyticsId), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
