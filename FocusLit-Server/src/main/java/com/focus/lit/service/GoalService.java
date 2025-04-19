package com.focus.lit.service;

import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;

public interface GoalService {
    Goal createGoal(Goal goal);
    void deleteGoal(GoalDto goal);
}
