package com.focus.lit.controller;

import com.focus.lit.dto.EndSessionDto;
import com.focus.lit.dto.SessionDto;
import com.focus.lit.dto.SessionUpdateDto;
import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.mapper.TagMapper;
import com.focus.lit.model.Session;
import com.focus.lit.model.User;
import com.focus.lit.service.SessionService;
import com.focus.lit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @Autowired
    private SessionMapper sessionMapper;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SessionDto sessionDto) {
        try {
            Session s= sessionService.createSession(sessionDto);
            //TODO: use mapper
            return new ResponseEntity<>(s, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @GetMapping("/getByUserId")
    public ResponseEntity<?> getAllSessionsByUserId(@RequestParam int userId) {
        try {
            List<Session> sessionList = sessionService.getSessionsByUserId(userId);
            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    };

    @PostMapping("/endSession")
    public ResponseEntity<?> endSession(@RequestBody EndSessionDto endSessionDto) throws Exception {
        sessionService.endSession(endSessionDto);
        return ResponseEntity.ok("Session ended successfully");
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateSession(@RequestBody SessionUpdateDto dto) {
        try {
            sessionService.updateSession(dto);
            return new ResponseEntity<>("Session updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}
