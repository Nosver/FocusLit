package com.focus.lit.service;

import com.focus.lit.model.Session;

import java.util.List;

public interface SessionService {
    Session createSession(Session session);

    List<Session> getSessionsByUserId(int userId) throws Exception;
}