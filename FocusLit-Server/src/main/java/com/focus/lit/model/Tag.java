package com.focus.lit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Nullable
    private Tag parent;

    private String name;

    @Column(name = "total_work_duration")
    private int totalWorkDuration;

    @Column(name = "full_path", nullable = false, unique = true)
    private String fullPath;

    @Column(name = "t_thread_id", nullable = false)
    private Integer tThreadId;

}
