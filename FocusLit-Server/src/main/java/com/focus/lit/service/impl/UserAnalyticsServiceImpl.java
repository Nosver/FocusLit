package com.focus.lit.service.impl;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.WeeklyWorkDto;
import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.mapper.UserAnalyticsMapper;
import com.focus.lit.model.Achievement;
import com.focus.lit.model.Session;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.UserAnalyticsRepository;
import com.focus.lit.service.SessionService;
import com.focus.lit.service.UserAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAnalyticsServiceImpl implements UserAnalyticsService {

    @Autowired
    UserAnalyticsRepository userAnalyticsRepository;

    @Autowired
    UserAnalyticsMapper userAnalyticsMapper;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionMapper sessionMapper;

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

    @Override
    public UserAnalyticsDto update(UserAnalyticsDto userAnalyticsDto) {
        Optional<UserAnalytics> userAnalytics = userAnalyticsRepository.findById(userAnalyticsDto.getId());
        if(userAnalytics.isEmpty()){
            throw new RuntimeException("User Analytics with given id not found: " + userAnalyticsDto.getId());
        }
        userAnalytics.get().setScore(userAnalyticsDto.getScore());
        userAnalytics.get().setTotalWorkDuration(userAnalyticsDto.getTotalWorkDuration());
        userAnalytics.get().setStreak(userAnalyticsDto.getStreak());
        userAnalytics.get().setUserRank(userAnalyticsDto.getUserRank());
        return userAnalyticsMapper.userAnalyticsToUserAnalyticsDto(userAnalytics.get());
    }

    @Override
    public void updateTotalWorkDuration(int gainedWorkDuration, int userAnalyticsId) {
        userAnalyticsRepository.updateTotalWorkDuration(gainedWorkDuration, userAnalyticsId);
    }

    @Override
    public WeeklyWorkDto getWeeklyWork(int userId) {
        // FIXME: In Progress
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime weekStartDate = currentDate.with(DayOfWeek.MONDAY); // FIXME: Not working properly
        LocalDateTime weekEndDate = currentDate.with(DayOfWeek.SUNDAY); // FIXME: Not working properly

        WeeklyWorkDto weeklyWorkDto = new WeeklyWorkDto();
        weeklyWorkDto.setStartDate(Timestamp.valueOf(weekStartDate));
        weeklyWorkDto.setEndDate(Timestamp.valueOf(weekEndDate));

        List<Session> sessions = sessionService.getSessionsByUserIdBetweenDates(userId, weekEndDate, weekStartDate);
        List<SessionDto> sessionDtos = new ArrayList<>();
        for (Session session : sessions) {
            sessionDtos.add(sessionMapper.sessionToSessionDto(session));
        }
        weeklyWorkDto.setSessions(sessionDtos);
        return weeklyWorkDto;
    }
}
