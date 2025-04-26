package com.focus.lit.mapper;


import com.focus.lit.dto.TagDto;
import com.focus.lit.model.Tag;
import com.focus.lit.service.TagService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = TagService.class)
public interface TagMapper {
    @Mapping(source = "parent.id", target = "parentId")
    TagDto tagToTagDto(Tag session);

    // If parent tag does not exists set parent Integer obj to null if exists fetch and set
    @Mapping(target = "parent", expression = "java(dto.getParentId() != null ? tagService.findById(dto.getParentId()) : null)")
    Tag tagDtoToTag(TagDto dto);
}
