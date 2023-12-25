package com.example.taskflow.mapper;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    Tag toEntity(Tag tag);
    TagDto toDto(Tag tag);
    List<TagDto> tagsToDTOs(List<Tag> tags);

}
