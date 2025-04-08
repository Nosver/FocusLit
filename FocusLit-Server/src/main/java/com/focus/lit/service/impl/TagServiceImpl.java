package com.focus.lit.service.impl;

import com.focus.lit.dto.TagDto;
import com.focus.lit.mapper.TagMapper;
import com.focus.lit.model.Tag;
import com.focus.lit.repository.TagRepository;
import com.focus.lit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::tagToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public void incrementTotalWorkDuration(int increment, Tag tag) {

        // FIXME: Implement depthLimit in createTag
        final int depthLimit = 4;

        if(tag == null) {
            throw new NullPointerException("Given tag is null");
        }

        tag.setTotalWorkDuration(tag.getTotalWorkDuration() + increment);
        tagRepository.save(tag);

        // TODO: Test this
        Tag currentTag = tag;
        for (int i = 0; i < depthLimit; i++) {
            currentTag = currentTag.getParent();
            if(currentTag == null) { break; }
            currentTag.setTotalWorkDuration(currentTag.getTotalWorkDuration() + increment);
            tagRepository.save(currentTag);
        }
    }
}
