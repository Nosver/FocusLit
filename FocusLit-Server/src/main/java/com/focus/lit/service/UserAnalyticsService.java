package com.focus.lit.service;

import com.focus.lit.model.UserAnalytics;

import java.util.Optional;

public interface UserAnalyticsService {
    UserAnalytics createUserAnalytics();
    Optional<UserAnalytics> getUserAnalytics(int id);
}
