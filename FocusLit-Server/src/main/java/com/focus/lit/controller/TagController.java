package com.focus.lit.controller;

import com.focus.lit.dto.TagDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.mapper.TagMapper;
import com.focus.lit.model.Tag;
import com.focus.lit.service.TagService;
import com.focus.lit.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TagDto tagDto) {
        try{
            Tag tag = tagMapper.tagDtoToTag(tagDto);
            Tag createdTag = tagService.create(tag);
            TagDto createdTagDto = tagMapper.tagToTagDto(createdTag);
            return new ResponseEntity<>(createdTagDto, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
