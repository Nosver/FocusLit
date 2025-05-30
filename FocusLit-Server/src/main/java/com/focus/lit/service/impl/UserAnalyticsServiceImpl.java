package com.focus.lit.service.impl;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.AddAchievementDto;
import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.dto.WeeklyWorkDto;
import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.mapper.UserAnalyticsMapper;
import com.focus.lit.model.Achievement;
import com.focus.lit.model.Session;
import com.focus.lit.model.UserAnalytics;
import com.focus.lit.repository.AchievementRepository;
import com.focus.lit.repository.UserAnalyticsRepository;
import com.focus.lit.service.SessionService;
import com.focus.lit.service.UserAnalyticsService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
    @Autowired
    private UserAnalyticsService userAnalyticsService;
    @Autowired
    private UserService userService;

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
    public Optional<UserAnalytics> getUserAnalytics(int userId) {
        return getUserAnalyticsByUserId(userId);
    }

    @Override
    public List<Achievement> getUserAchievements(int userAnalyticsId) throws Exception {
        Optional<UserAnalytics> userAnalytics = userAnalyticsRepository.findById(userAnalyticsId);
        if(userAnalytics.isEmpty()) throw new Exception("User Analytics with given id not found");
        return userAnalytics.get().getUserAchievements();
    }
    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public void addAchievement(AddAchievementDto dto) throws Exception {
        UserAnalytics userAnalytics = userAnalyticsRepository.findById(dto.getUserAnalyticsId())
                .orElseThrow(() -> new Exception("UserAnalytics not found"));

        Achievement achievement = achievementRepository.findById(dto.getAchievementId())
                .orElseThrow(() -> new Exception("Achievement not found"));

        List<Achievement> currentAchievements = userAnalytics.getUserAchievements();
        if (!currentAchievements.contains(achievement)) {
            currentAchievements.add(achievement);
        }

        userAnalytics.setUserAchievements(currentAchievements);
        userAnalyticsRepository.save(userAnalytics);
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
        LocalDateTime now = LocalDateTime.now();
        LocalDate currentDate = now.toLocalDate();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        DayOfWeek firstDayOfWeek = weekFields.getFirstDayOfWeek();

        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusWeeks(1);

        LocalDateTime startDateTime = startOfWeek.atStartOfDay().plusDays(1);
        LocalDateTime endDateTime = endOfWeek.atStartOfDay().plusDays(1);

        List<Session> sessions = sessionService.getSessionsByUserIdBetweenDates(userId, startDateTime, endDateTime);
        List<SessionDto> sessionDtos = new ArrayList<>();
        for (Session session : sessions) {
            sessionDtos.add(sessionMapper.sessionToSessionDto(session));
        }
        WeeklyWorkDto weeklyWorkDto = new WeeklyWorkDto();
        weeklyWorkDto.setSessions(sessionDtos);
        weeklyWorkDto.setStartDate(startDateTime);
        weeklyWorkDto.setEndDate(endDateTime);
        return weeklyWorkDto;
    }

    @Override
    public Optional<UserAnalytics> getUserAnalyticsByUserId(int userId) {
        return userService.getUserAnalytics(userId);
    }

    @Override
    public int getRankByUserAnalyticsId(int id){
        return userAnalyticsRepository.getRankFromUserAnalyticsId(id);
    }
}
