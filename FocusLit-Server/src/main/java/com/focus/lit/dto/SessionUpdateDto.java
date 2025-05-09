package com.focus.lit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionUpdateDto {
    private int sessionId;
    private LocalDateTime endTime;
    private int completedWorkDuration;
    private int score;
    private boolean isCompleted;
}
