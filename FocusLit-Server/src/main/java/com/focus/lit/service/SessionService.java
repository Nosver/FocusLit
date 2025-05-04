package com.focus.lit.service;

import com.focus.lit.dto.EndSessionDto;
import com.focus.lit.dto.SessionDto;
import com.focus.lit.model.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {
    Session createSession(SessionDto session) throws Exception;

    List<Session> getSessionsByUserIdBetweenDates(int userId) throws Exception;

    void endSession(EndSessionDto sessionInfo) throws Exception;

    List<Session> getSessionsByUserIdBetweenDates(int userId, LocalDateTime endDate, LocalDateTime startDate);
}