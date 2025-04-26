package com.focus.lit.dto;

import com.focus.lit.model.Tag;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDto {
    private int id;
    private String name;
    private int totalWorkDuration;
    private Integer parentId; // Nullable in case of root tag

}
