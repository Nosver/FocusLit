package com.focus.lit.service.impl;

import com.focus.lit.dto.ErrorMessageDto;
import com.focus.lit.dto.GoalCreateDto;
import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import com.focus.lit.model.Tag;
import com.focus.lit.model.User;
import com.focus.lit.repository.GoalRepository;
import com.focus.lit.repository.TagRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public ResponseEntity<?> createGoal(GoalCreateDto goalCreateDto) {
        Optional<Tag> existingTag = tagRepository.findById(goalCreateDto.getTagId());
        if(existingTag.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto("Selected tag doesn't exist!"));
        Optional<User> existingUser = userRepository.findById(goalCreateDto.getUserId());
        if(existingUser.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDto("User with given id doesn't exist!"));

        Goal goal = new Goal();
        goal.setCompletedWorkDuration(0);
        goal.setTargetWorkDuration(goalCreateDto.getTargetWorkDuration());
        goal.setStartTime(LocalDateTime.now());
        goal.setTag(existingTag.get());
        goal.setUser(existingUser.get());

        goalRepository.save(goal);
        return ResponseEntity.ok().build();
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
