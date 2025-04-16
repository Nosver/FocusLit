package com.focus.lit.service.impl;

import com.focus.lit.model.Achievement;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.UserAnalyticsRepository;
import com.focus.lit.service.UserAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAnalyticsServiceImpl implements UserAnalyticsService {

    @Autowired
    UserAnalyticsRepository userAnalyticsRepository;

    @Override
    public UserAnalytics createUserAnalytics() {
        UserAnalytics userAnalytics = new UserAnalytics();
        userAnalytics.setTotalWorkDuration(0);
        userAnalytics.setScore(0);
        userAnalytics.setStreak(0);
        userAnalytics.setUserRank(-1);
        return userAnalyticsRepository.save(userAnalytics);
    }

    @Override
    public Optional<UserAnalytics> getUserAnalytics(int id) {
        return userAnalyticsRepository.findById(id);
    }

    @Override
    public List<Achievement> getUserAchievements(int userAnalyticsId) throws Exception {
        Optional<UserAnalytics> userAnalytics = userAnalyticsRepository.findById(userAnalyticsId);
        if(userAnalytics.isEmpty()) throw new Exception("User Analytics with given id not found");
        return userAnalytics.get().getUserAchievements();
    }


}
