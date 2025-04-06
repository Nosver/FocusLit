package com.focus.lit.mapper;


import com.focus.lit.dto.TagDto;
import com.focus.lit.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(source = "parent.id", target = "parentId")
    TagDto tagToTagDto(Tag session);
    @Mapping(source = "parentId", target = "parent.id")
    Tag tagDtoToTag(TagDto dto);
}
