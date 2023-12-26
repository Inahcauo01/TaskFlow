package com.example.taskflow.mapper;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.dto.TagDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagMapper {
    private TagMapper() {}

    public static TagDto toDto(Tag tag){
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static Tag toEntity(TagDto tagDto){
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}
