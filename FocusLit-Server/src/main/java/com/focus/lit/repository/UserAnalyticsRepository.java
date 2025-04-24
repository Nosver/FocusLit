package com.focus.lit.repository;

import com.focus.lit.model.UserAnalytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnalyticsRepository extends JpaRepository<UserAnalytics, Integer> {

    @Modifying
    @Query("UPDATE UserAnalytics u set u.totalWorkDuration=u.totalWorkDuration+ :gainedWorkDuration WHERE u.id=:userAnalyticsId  ")
    void updateTotalWorkDuration(@Param("gainedWorkDuration") int gainedWorkDuration, @Param("userAnalyticsId") int userAnalyticsId);
}
