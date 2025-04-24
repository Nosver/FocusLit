package com.focus.lit.service;

import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;

import java.util.List;

public interface GoalService {
    Goal createGoal(Goal goal);
    void deleteGoal(GoalDto goal);
    List<Goal> getActiveGoalByUserIdAndTagId(int UserId, int tagId);

    Goal saveGoal(Goal goal);
}
