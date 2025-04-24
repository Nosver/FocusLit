package com.focus.lit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndSessionDto {
    private int sessionId;
    private int completedMinutes;
}
