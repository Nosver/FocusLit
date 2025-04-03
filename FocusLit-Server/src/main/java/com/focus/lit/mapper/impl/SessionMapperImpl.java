package com.focus.lit.mapper.impl;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.model.Session;
import com.focus.lit.model.Tag;
import com.focus.lit.model.User;
import com.focus.lit.repository.TagRepository;
import com.focus.lit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements SessionMapper {

    @Override
    public SessionDto toDto(Session session) {
        if (session == null) {
            return null;
        }

        SessionDto sessionDto = new SessionDto();
        sessionDto.setUserId(session.getUser().getId());
        sessionDto.setTagId(session.getTag().getId());
        sessionDto.setStartTime(session.getStartTime());
        sessionDto.setWorkDuration(session.getWorkDuration());
        sessionDto.setWaitDuration(session.getWaitDuration());
        sessionDto.setScore(session.getScore());
        sessionDto.setScoreMultiplier(session.getScoreMultiplier());
        sessionDto.setCompleted(session.isCompleted());

        return sessionDto;
    }

    @Override
    public Session toEntity(SessionDto sessionDto) {
        if (sessionDto == null) {
            return null;
        }

        Session session = new Session();
        session.setUser(fetchUserById(sessionDto.getUserId())); // TODO: Create user objects using dto.userId then set
        session.setTag(fetchTagById(sessionDto.getTagId())); // TODO
        session.setStartTime(sessionDto.getStartTime());
        session.setWorkDuration(sessionDto.getWorkDuration());
        session.setWaitDuration(sessionDto.getWaitDuration());
        session.setScore(sessionDto.getScore());
        session.setScoreMultiplier(sessionDto.getScoreMultiplier());
        session.setCompleted(sessionDto.isCompleted());

        return session;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    private User fetchUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
    }

    private Tag fetchTagById(int tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag with id " + tagId + " not found"));
    }
}