package com.focus.lit.repository;

import com.focus.lit.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByUserId(int userId);

    List<Session> findCompletedSessionsByUserId(int id);

    List<Session> findByStartTimeBetween(LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);

    List<Session> findByUserIdAndStartTimeBetween(int userId, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}
