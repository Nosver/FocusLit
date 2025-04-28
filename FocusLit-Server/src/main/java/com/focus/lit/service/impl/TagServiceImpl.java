package com.focus.lit.service.impl;

import com.focus.lit.dto.TagDto;
import com.focus.lit.mapper.TagMapper;
import com.focus.lit.model.Tag;
import com.focus.lit.repository.TagRepository;
import com.focus.lit.service.TagService;
import com.focus.lit.service.TelegramService;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TelegramService telegramService;

    final int depthLimit = 4;

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::tagToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public void incrementTotalWorkDuration(int increment, Tag tag) {

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

    private int getDepth(Tag tag) {
        int depth = 0;
        Tag currentTag = tag;
        for (int i = 0; i < 50; i++){
            currentTag = currentTag.getParent();
            depth++;
            if(currentTag == null) { break; }
        }
        return depth;
    }

    private String buildFullPath(Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Tag is null");
        }

        List<String> names = new ArrayList<>();
        Tag current = tag;
        int counter = 0;

        while (current != null) {
            names.add(current.getName());
            current = current.getParent();
            counter++;

            if (counter > 50) {
                throw new IllegalStateException("Tag hierarchy too deep! (50+). This should NEVER happen");
            }
        }

        Collections.reverse(names);

        if (names.isEmpty()) {
            throw new IllegalStateException("Cannot build path for empty tag names");
        }

        return String.join("/", names);
    }


    @Override
    public Tag create(Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Given tag is null");
        }

        tag.setFullPath(buildFullPath(tag));
        int depth = getDepth(tag);

        if (depth > depthLimit) {
            throw new InternalException("Depth limit exceeded!");
        }

        Integer threadId = telegramService.createForumTopic(tag.getFullPath());
        tag.setTThreadId(threadId);

        return tagRepository.save(tag);
    }


    @Override
    public Tag findById(int id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public void incrementTotalWorkDuration(int gainedWorkDuration, int tagId) {
        tagRepository.incrementTotalWorkDuration(gainedWorkDuration, tagId);
    }

    @Override
    public List<Tag> getSubTags(Tag tag) {
        List<Tag> result = new ArrayList<>();
        result.add(tag);
        fetchSubTagsRecursive(tag, result, 1);
        return result;
    }

    private void fetchSubTagsRecursive(Tag parent, List<Tag> result, int depth) {
        if (depth > depthLimit) {
            return;
        }

        List<Tag> children = tagRepository.findByParent(parent);
        result.addAll(children);

        for (Tag child : children) {
            fetchSubTagsRecursive(child, result, depth + 1);
        }
    }
}
