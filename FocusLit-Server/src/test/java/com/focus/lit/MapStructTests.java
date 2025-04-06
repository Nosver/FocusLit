package com.focus.lit;

import com.focus.lit.mapper.SessionMapper;
import com.focus.lit.model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
public class MapStructTests {

    @Autowired
    private SessionMapper sessionMapper;

    //private final SessionMapper sessionMapper = new SessionMapperImpl();

    @Test
    public void sessionMapperTest(){

        Session session = new Session();
        session.setWorkDuration(10);
        session.setWaitDuration(5);

        Session session1 = sessionMapper.sessionDtoToSession(sessionMapper.sessionToSessionDto(session));

        Assertions.assertEquals(session.getWorkDuration(), session1.getWorkDuration());
        Assertions.assertEquals(session.getWaitDuration(), session1.getWaitDuration());



    }
}
