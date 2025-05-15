package com.focus.lit.repository;

import com.focus.lit.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    @Query("SELECT g FROM Goal g WHERE g.tag.id= :tagId AND g.user.id= :userId AND g.completedWorkDuration < g.targetWorkDuration" )
    List<Goal> getActiveGoalByUserIdAndTagId(@Param("userId") int userId,@Param("tagId") int tagId);

    List<Goal> getGoalsByUserId(@Param("userId") int userId);
}
