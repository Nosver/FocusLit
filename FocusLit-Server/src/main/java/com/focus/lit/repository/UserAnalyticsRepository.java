package com.focus.lit.repository;

import com.focus.lit.model.UserAnalytics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnalyticsRepository extends JpaRepository<UserAnalytics, Integer> {
}
