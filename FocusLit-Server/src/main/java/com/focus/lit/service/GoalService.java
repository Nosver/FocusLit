package com.focus.lit.service;

import com.focus.lit.dto.GoalCreateDto;
import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GoalService {
    ResponseEntity<?> createGoal(GoalCreateDto goalCreateDto);
    void deleteGoal(GoalDto goal);
    List<Goal> getActiveGoalByUserIdAndTagId(int UserId, int tagId);

    Goal saveGoal(Goal goal);

    ResponseEntity<?> getGoals(int userId);
}
