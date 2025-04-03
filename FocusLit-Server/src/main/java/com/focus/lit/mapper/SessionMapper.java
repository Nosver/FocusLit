package com.focus.lit.mapper;

import com.focus.lit.dto.SessionDto;
import com.focus.lit.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionDto toDto(Session session);
    Session toEntity(SessionDto dto);
}