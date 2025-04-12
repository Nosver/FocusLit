package com.focus.lit.service.impl;

import com.focus.lit.model.Session;
import com.focus.lit.model.User;
import com.focus.lit.repository.SessionRepository;
import com.focus.lit.repository.UserRepository;
import com.focus.lit.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessionsByUserId(int userId) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);
        if(!existingUser.isPresent()){
            throw new Exception("User with given id doesn't exist");
        }
        return sessionRepository.findByUserId(userId);
    }
}
