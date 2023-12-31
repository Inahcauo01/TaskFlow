package com.example.taskflow.service.impl;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.dto.TagDto;
import com.example.taskflow.repository.TagRepository;
import com.example.taskflow.service.TagService;
import com.example.taskflow.utils.CustomError;
import com.example.taskflow.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) throws ValidationException {
        return tagRepository.findById(id).orElseThrow(
                () -> new ValidationException(new CustomError("id", "Tag with id " + id + " not found")));
    }

    @Override
    public Tag save(Tag tag) throws ValidationException {
        validateTage(tag);
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        // TODO: validate tag
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) throws ValidationException {
        if (!tagRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "Tag with id " + id + " not found"));
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findOrCreateTag(TagDto tagName) {
        return tagRepository.findByName(tagName.getName()).orElseGet(
                () -> {
                    try {
                        return save(Tag.builder()
                                    .name(tagName.getName())
                                    .build()
                            );
                    } catch (ValidationException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
        );
    }

    private void validateTage(Tag tag) throws ValidationException {
    // check if tag name is empty
        if (tag.getName().isEmpty())
            throw new ValidationException(new CustomError("name", "Tag name cannot be empty"));
    // check if tag name is already taken
        if (tagRepository.findByName(tag.getName()).isPresent())
            throw new ValidationException(new CustomError("name", "Tag name already taken"));
    }
}
