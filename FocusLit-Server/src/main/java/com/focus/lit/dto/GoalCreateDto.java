package com.focus.lit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoalCreateDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int targetWorkDuration;
    private int tagId;  // sadece ID
}
