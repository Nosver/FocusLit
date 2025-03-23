package com.focus.lit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "user_analytics_id", referencedColumnName = "id")
    private UserAnalytics userAnalytics;

}
