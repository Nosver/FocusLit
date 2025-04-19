package com.focus.lit.dto;

import com.focus.lit.model.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoalDto {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Tag tag;
    private int targetWorkDuration;
}
