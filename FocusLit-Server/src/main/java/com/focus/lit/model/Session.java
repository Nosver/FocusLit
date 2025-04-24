package com.focus.lit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "work_duration")
    private int workDuration;

    @Column(name = "wait_duration")
    private int waitDuration;

    private int score;

    @Column(name = "score_multiplier")
    private float scoreMultiplier;

    @Column(name = "is_completed")
    private boolean isCompleted;
    
    @Column(name="completed_work_duration")
    private int completedWorkDuration;

}