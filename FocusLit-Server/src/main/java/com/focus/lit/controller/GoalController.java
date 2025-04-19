package com.focus.lit.controller;

import com.focus.lit.dto.GoalDto;
import com.focus.lit.model.Goal;
import com.focus.lit.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGoal(@RequestBody GoalDto goal) {
        goalService.deleteGoal(goal);
        return ResponseEntity.ok(null);
    }
}
