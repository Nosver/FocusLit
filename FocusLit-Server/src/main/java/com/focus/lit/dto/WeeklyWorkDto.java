package com.focus.lit.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class WeeklyWorkDto {
    List<SessionDto> sessions;
    Date startDate;
    Date endDate;
}
