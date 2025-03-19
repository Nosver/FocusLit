package com.focus.lit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int point;

    // FIXME: Problem here!
    @ManyToOne
    @JoinColumn(name = "user_analytics_id", referencedColumnName = "id")
    private UserAnalytics userAnalytics;

}
