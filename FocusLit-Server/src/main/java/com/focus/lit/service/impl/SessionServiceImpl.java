package com.focus.lit.service.impl;

import com.focus.lit.model.Session;
import com.focus.lit.repository.SessionRepository;
import com.focus.lit.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session createSession(Session session) {
        // TODO: Check if any start time is overlapping in db, if so return error message
        return sessionRepository.save(session);
    }
}
