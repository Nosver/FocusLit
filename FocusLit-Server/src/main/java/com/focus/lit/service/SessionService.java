package com.focus.lit.service;

import com.focus.lit.dto.EndSessionDto;
import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.SessionUpdateDto;
import com.focus.lit.model.Session;

import java.util.List;

public interface SessionService {
    Session createSession(SessionDto session) throws Exception;

    List<Session> getSessionsByUserId(int userId) throws Exception;

    void endSession(EndSessionDto sessionInfo) throws Exception;

    void updateSession(SessionUpdateDto dto) throws Exception;

}