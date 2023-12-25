package com.example.taskflow.service;

import com.example.taskflow.domain.Tag;
import com.example.taskflow.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    public List<Tag> findAll();
    public Tag findById(Long id) throws ValidationException;
    public Tag save(Tag tag);
    public Tag update(Tag tag);
    public void deleteById(Long id) throws ValidationException;
}
