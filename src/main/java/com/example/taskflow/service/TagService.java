package com.example.taskflow.service;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.dto.TagDto;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<Tag> findAll();
    Tag findById(Long id) throws ValidationException;
    Tag save(Tag tag);
    Tag update(Tag tag);
    void deleteById(Long id) throws ValidationException;

    Tag findOrCreateTag(TagDto tagName);
}
