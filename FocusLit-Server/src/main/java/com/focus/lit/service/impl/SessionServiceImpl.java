package com.focus.lit.service.impl;

import com.focus.lit.dto.EndSessionDto;
import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.SessionUpdateDto;
import com.focus.lit.model.*;
import com.focus.lit.repository.SessionRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAnalyticsService userAnalyticsService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    private final double dayMultiplier = 1.1;
    private final double scoreByMinute = 1.0;
    private final double toleranceInMinute = 5.0;


    private int getCurrentDayStreak(Session session) {
        Optional<User> userOptional = userRepository.findById(session.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find user");
        }

        User user = userOptional.get();
        List<Session> completedSessions = sessionRepository.findCompletedSessionsByUserId(user.getId());

        int streak = 0;
        LocalDate today = LocalDate.now();

        for (Session completedSession : completedSessions) {
            LocalDate sessionDate = completedSession.getStartTime().toLocalDate();

            if (sessionDate.isEqual(today)) {
                streak++;
            }
        }

        return streak;
    }


    private double getSessionScore(Session session) { // FIXME: THis is not recognizing the completed instead taking all
        if (session == null) {
            throw new IllegalArgumentException("Cannot find session");
        }
        if (session.isCompleted()) {
            return session.getWorkDuration() * scoreByMinute * session.getScoreMultiplier();
        } else {
            return session.getCompletedWorkDuration() * scoreByMinute;
        }
    }

    private double getMult(int streak) {
        double mult = 1.0;
        for (int i = 0; i < streak; i++) {
            mult *= dayMultiplier;
        }
        return mult;
    }

    @Override
    public Session createSession(SessionDto sessionDto) throws Exception {
        Optional<User> u = userService.getById(sessionDto.getUserId());
        if (u.isEmpty()) {
            throw new Exception("Invalid UserId for session");
        }

        Tag t = tagService.findById(sessionDto.getTagId());

        Session session = new Session();
        session.setUser(u.get());
        session.setTag(t);
        session.setStartTime(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")).toLocalDateTime());
        session.setWorkDuration(sessionDto.getWorkDuration());
        session.setWaitDuration(sessionDto.getWaitDuration());
        session.setCompleted(false);

        session.setCompletedWorkDuration(0);
        session.setScoreMultiplier(getMult(getCurrentDayStreak(session)));
        session.setScore(0);

        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessionsByUserIdBetweenDates(int userId) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) {
            throw new Exception("User with given id doesn't exist");
        }
        return sessionRepository.findByUserId(userId);
    }
    @Override
    public void updateSession(SessionUpdateDto dto) throws Exception {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new Exception("Session not found"));

        session.setEndTime(dto.getEndTime());
        session.setCompletedWorkDuration(dto.getCompletedWorkDuration());
        session.setScore(dto.getScore());
        session.setCompleted(dto.isCompleted());

        sessionRepository.save(session);
    }


    @Override
    @Transactional
    public void endSession(EndSessionDto sessionInfo) throws Exception {
        //update user's session
        Optional<Session> s = sessionRepository.findById(sessionInfo.getSessionId());
        if (s.isEmpty()) {
            throw new Exception("Session with given id doesn't exist");
        }

        Session session = s.get();
        if (session.isCompleted()) {
            throw new Exception("Session already completed");
        }

        // Check if session creds valid
        if(sessionInfo.getCompletedMinutes() < session.getWorkDuration() - toleranceInMinute){ // Not completed, worked too little
            session.setCompleted(false);
            session.setCompletedWorkDuration(sessionInfo.getCompletedMinutes());
            session.setScore(getSessionScore(session));
        }
        else if(sessionInfo.getCompletedMinutes() > session.getWorkDuration() + toleranceInMinute){ // When this case happens user has left the session open and target time exceeded meaning creds are not valid
            session.setCompleted(false);
            session.setCompletedWorkDuration(0);
            session.setScore(getSessionScore(session));
        }
        else{
            session.setCompleted(true);
            session.setCompletedWorkDuration(sessionInfo.getCompletedMinutes());
            session.setScore(getSessionScore(session));
        }
        session.setEndTime(LocalDateTime.now());
        sessionRepository.save(session);

        //Handle goal progresses both for parent and children
        Tag t = session.getTag();
        List<Tag> subTags = tagService.getSubTags(t);

        subTags.forEach(tag -> {

            //increment children tag's total work duration
            tagService.incrementTotalWorkDuration(sessionInfo.getCompletedMinutes(), tag.getId());


            List<Goal> userGoals = goalService.getActiveGoalByUserIdAndTagId(session.getUser().getId(), tag.getId());
            userGoals.forEach(goal -> {
                goal.setCompletedWorkDuration(sessionInfo.getCompletedMinutes() + goal.getCompletedWorkDuration());

                goalService.saveGoal(goal);
            });

        });


        //handle user analytics
        UserAnalytics userAnalytics = session.getUser().getUserAnalytics();
        userAnalyticsService.updateTotalWorkDuration(sessionInfo.getCompletedMinutes(), userAnalytics.getId());
        //TODO: increment strike if today's first completed session

    }

    @Override
    public List<Session> getSessionsByUserIdBetweenDates(int userId, LocalDateTime endDate, LocalDateTime startDate) {
        return sessionRepository.findByUserIdAndStartTimeBetween(userId, endDate, startDate);
    }
}
