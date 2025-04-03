package com.focus.lit.config;

import com.focus.lit.mapper.SessionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public SessionMapper sessionMapper() {
        return Mappers.getMapper(SessionMapper.class);
    }

}