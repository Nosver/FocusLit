package com.focus.lit.mapper;

import com.focus.lit.dto.UserAnalyticsDto;
import com.focus.lit.model.UserAnalytics;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserAnalyticsMapper {
    UserAnalyticsDto userAnalyticsToUserAnalyticsDto(UserAnalytics userAnalytics);
    UserAnalytics userAnalyticsDtoToUserAnalytics(UserAnalyticsDto userAnalyticsDto);
}
