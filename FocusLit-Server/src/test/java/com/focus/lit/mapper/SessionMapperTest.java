package com.focus.lit.mapper;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.model.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessionMapperTest {

    private final SessionMapper sessionMapper = SessionMapper.INSTANCE;

    @Test
    public void sessionToSessionDto() {
        // Create a sample Session object
        Session session = new Session();

        session.setStartTime(LocalDateTime.of(2023, 10, 10, 10, 0));
        session.setWorkDuration(120);
        session.setWaitDuration(30);
        session.setScore(100);
        session.setScoreMultiplier(1.5f);
        session.setCompleted(true);

        // Map Session to SessionDto
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert the mapping results
        assertEquals(session.getStartTime(), sessionDto.getStartTime());
        assertEquals(session.getWorkDuration(), sessionDto.getWorkDuration());
        assertEquals(session.getWaitDuration(), sessionDto.getWaitDuration());
        assertEquals(session.getScore(), sessionDto.getScore());
        assertEquals(session.getScoreMultiplier(), sessionDto.getScoreMultiplier());
        assertEquals(session.isCompleted(), sessionDto.isCompleted());
    }
}