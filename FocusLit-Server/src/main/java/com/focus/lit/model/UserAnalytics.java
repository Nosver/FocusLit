package com.focus.lit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_analytics")
public class UserAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int streak;

    @Column(name = "total_work_duration")
    private int totalWorkDuration;

    private int score;

    @Column(name = "user_rank")
    private int userRank;

    @OneToMany(mappedBy = "userAnalytics")
    private List<Achievement> achievements;

}
