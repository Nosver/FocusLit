package com.focus.lit.service.impl;

import com.focus.lit.dto.EndSessionDto;
import com.focus.lit.model.*;
import com.focus.lit.repository.SessionRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.GoalService;
import com.focus.lit.service.SessionService;
import com.focus.lit.service.TagService;
import com.focus.lit.service.UserAnalyticsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessionsByUserId(int userId) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);
        if(!existingUser.isPresent()){
            throw new Exception("User with given id doesn't exist");
        }
        return sessionRepository.findByUserId(userId);
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
        if(session.isCompleted()){ throw new Exception("Session already completed"); }
        session.setCompleted(true);
        session.setCompletedWorkDuration(sessionInfo.getCompletedMinutes());

        //TODO: handle score and score multiplier logic
        sessionRepository.save(session);


        //Handle goal progresses both for parent and children
        Tag t =session.getTag();
        List<Tag> subTags= tagService.getSubTags(t);

        subTags.forEach(tag -> {

            //increment children tag's total work duration
            tagService.incrementTotalWorkDuration(sessionInfo.getCompletedMinutes(),tag.getId());


            List<Goal> userGoals= goalService.getActiveGoalByUserIdAndTagId(session.getUser().getId(), tag.getId());
            userGoals.forEach(goal -> {
                goal.setCompletedWorkDuration(sessionInfo.getCompletedMinutes()+  goal.getCompletedWorkDuration());

                goalService.saveGoal(goal);
            });

        });


        //handle user analytics
        UserAnalytics userAnalytics = session.getUser().getUserAnalytics();
        userAnalyticsService.updateTotalWorkDuration(sessionInfo.getCompletedMinutes(),userAnalytics.getId());
        //TODO: increment strike if today's first completed session

    }
}
