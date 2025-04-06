package com.focus.lit.mapper;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.model.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionDto sessionToSessionDto(Session session);
    Session sessionDtoToSession(SessionDto sessionDto);
}