package com.focus.lit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAnalyticsDto {
    private int id;

    private int streak;

    private int totalWorkDuration;

    private int userRank;
}
