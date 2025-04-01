package com.focus.lit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionDto {
    private int userId;
    private int tagId;
    private LocalDateTime startTime;
    private int workDuration;
    private int waitDuration;
    private int score;
    private float scoreMultiplier;

    @JsonProperty("isCompleted")
    private boolean isCompleted;
}