package com.focus.lit.controller;

import com.focus.lit.dto.TagDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
