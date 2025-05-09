package com.focus.lit.service;

import com.focus.lit.dto.AddAchievementDto;
import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.WeeklyWorkDto;
import com.focus.lit.model.Achievement;
import com.focus.lit.model.UserAnalytics;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserAnalyticsService {
    UserAnalytics createUserAnalytics();
    Optional<UserAnalytics> getUserAnalytics(int userId);
    List<Achievement> getUserAchievements(int id) throws Exception;
    UserAnalyticsDto update(UserAnalyticsDto userAnalyticsDto);
    void updateTotalWorkDuration(int gainedWorkDuration, int userAnalyticsId);
    void addAchievement(AddAchievementDto dto) throws Exception;


    WeeklyWorkDto getWeeklyWork(int userId);

    Optional<UserAnalytics> getUserAnalyticsByUserId(int userId);
}
