package com.focus.lit.repository;

import com.focus.lit.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> getTagById(int id);

    @Modifying
    @Query("UPDATE Tag t set t.totalWorkDuration=t.totalWorkDuration+:gainedWorkDuration WHERE t.id=:tagId ")
    void incrementTotalWorkDuration(@Param("gainedWorkDuration") int gainedWorkDuration, @Param("tagId")  int tagId);

    List<Tag> findByParent(Tag parent);
}
