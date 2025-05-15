package com.focus.lit.service.impl;

import com.focus.lit.dto.ErrorMessageDto;
import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import com.focus.lit.repository.GoalRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Goal createGoal(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("Goal object must be field.");
        }

        if (goal.getTag() == null) {
            throw new IllegalArgumentException("Goal's Tag field cannot be null.");
        }

        if (goal.getStartTime() == null ) {
            throw new IllegalArgumentException("Start time cannot be null.");
        }

        if (goal.getTargetWorkDuration() <= 0) {
            throw new IllegalArgumentException("Target uptime must be greater than 0.");
        }

       /* if (goal.getEndTime().isBefore(goal.getStartTime())) {
            throw new IllegalArgumentException("The end time cannot be before the start time.");
        }

        */

        return goalRepository.save(goal);
    }


    @Override
    public void deleteGoal(GoalDto goal) {
        if (goal.getId() == null) {
            throw new RuntimeException("Goal id is null");
        }
        if (!goalRepository.existsById(goal.getId())){throw new RuntimeException("Goal id is not found in db");}

        goalRepository.deleteById(goal.getId());

    }

    @Override
    public List<Goal> getActiveGoalByUserIdAndTagId(int UserId, int tagId) {
        return goalRepository.getActiveGoalByUserIdAndTagId(UserId, tagId);
    }

    @Override
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public ResponseEntity<?> getGoals(int userId) {
        if(!userRepository.existsById(userId)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto("User with given id doesn't exist!"));
        return ResponseEntity.ok().body(goalRepository.getGoalsByUserId(userId));
    }

}
