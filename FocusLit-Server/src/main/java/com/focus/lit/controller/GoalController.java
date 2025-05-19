package com.focus.lit.controller;

import com.focus.lit.dto.GoalCreateDto;
import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import com.focus.lit.model.Tag;
import com.focus.lit.service.GoalService;
import com.focus.lit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;
    @Autowired
    private TagService tagService;

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGoal(@RequestBody GoalDto goal) {
        goalService.deleteGoal(goal);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createGoal(@RequestBody GoalCreateDto goalCreateDto){
        try{
            return goalService.createGoal(goalCreateDto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error occurred while creating the goal: " + e.getMessage());
        }
    }

    @GetMapping("/getGoals")
        public ResponseEntity<?> getGoals(@RequestParam("userId") Integer userId){
        try{
            return goalService.getGoals(userId);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while getting goals: " + e.getMessage());
        }
    }

}
