package com.focus.lit.mapper;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    SessionDto sessionToSessionDto(Session session);
    Session sessionDtoToSession(SessionDto sessionDto);
}