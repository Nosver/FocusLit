package com.focus.lit.service;

import com.focus.lit.dto.AddAchievementDto;
import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.model.Achievement;
import com.focus.lit.model.UserAnalytics;

import java.util.List;
import java.util.Optional;

public interface UserAnalyticsService {
    UserAnalytics createUserAnalytics();
    Optional<UserAnalytics> getUserAnalytics(int id);
    List<Achievement> getUserAchievements(int id) throws Exception;
    UserAnalyticsDto update(UserAnalyticsDto userAnalyticsDto);
    void updateTotalWorkDuration(int gainedWorkDuration, int userAnalyticsId);
    void addAchievement(AddAchievementDto dto) throws Exception;

}
