package com.focus.lit;

import com.focus.lit.model.Tag;
import com.focus.lit.repository.TagRepository;
import com.focus.lit.service.TagService;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.random.RandomGenerator;

@SpringBootTest
public class TagServiceTests {

//    @Autowired
//    private TagService tagService;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    @Test
//    public void tagServiceTests() {
//
//        int increment = RandomGenerator.getDefault().nextInt();
//
//        int tagId = tagService.getAllTags().get(0).getId();
//        Optional<Tag> tag = tagRepository.findById(tagId);
//        tag.ifPresent(tag1 -> System.out.println(tag1));
//        int oldTotalWorkDuration = tag.get().getTotalWorkDuration();
//        System.out.println("Current Work Duration: " + tag.get().getTotalWorkDuration());
//        tagService.incrementTotalWorkDuration(increment, tag.get());
//
//        Assertions.assertEquals(increment + oldTotalWorkDuration, tag.get().getTotalWorkDuration());
//
//
//
//
//    }

}
