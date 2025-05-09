package com.focus.lit.mapper;


import com.focus.lit.dto.TagDto;
import com.focus.lit.model.Tag;
import com.focus.lit.service.TagService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class TagMapper {
    @Autowired
    protected TagService tagService;

    @Mapping(source = "parent.id", target = "parentId")
    public abstract TagDto tagToTagDto(Tag session);

    @Mapping(target = "parent", expression = "java(dto.getParentId() != null ? tagService.findById(dto.getParentId()) : null)")
    public abstract Tag tagDtoToTag(TagDto dto);


}
