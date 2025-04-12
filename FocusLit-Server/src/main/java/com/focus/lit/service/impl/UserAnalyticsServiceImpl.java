package com.focus.lit.service.impl;

import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.UserAnalyticsRepository;
import com.focus.lit.service.UserAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
