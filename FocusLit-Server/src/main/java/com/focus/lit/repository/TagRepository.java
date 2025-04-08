package com.focus.lit.repository;

import com.focus.lit.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> getTagById(int id);
}
