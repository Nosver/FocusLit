package com.focus.lit.service.impl;

import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import com.focus.lit.repository.GoalRepository;
import com.focus.lit.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal createGoal(Goal goal) {
        //TODO: implement this (Güney)
        return null;
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
}
