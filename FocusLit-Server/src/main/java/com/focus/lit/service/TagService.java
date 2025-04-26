package com.focus.lit.service;

import com.focus.lit.dto.TagDto;
import com.focus.lit.model.Tag;

import java.util.List;

public interface TagService {
    public List<TagDto> getAllTags();
    public void incrementTotalWorkDuration(int increment, Tag tag);
    void incrementTotalWorkDuration(int gainedWorkDuration, int tagId);
    List<Tag> getSubTags(Tag tag);
    public Tag create(Tag tag);
    public Tag findById(int id);
}
