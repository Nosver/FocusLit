package com.focus.lit.controller;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.model.Session;
import com.focus.lit.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    //@Autowired
    //private SessionMapper sessionMapper;

    @PostMapping("/create")
    public ResponseEntity<SessionDto> create(@RequestBody SessionDto sessionDto) {
//        try {
//            Session session = sessionMapper.toEntity(sessionDto);
//            Session createdSession = sessionService.createSession(session);
//            SessionDto createdSessionDto = sessionMapper.toDto(createdSession);
//            return new ResponseEntity<>(createdSessionDto, HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }




}
