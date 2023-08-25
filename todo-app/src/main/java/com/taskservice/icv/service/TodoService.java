package com.taskservice.icv.service;

import com.taskservice.icv.model.dto.TodoDto;
import com.taskservice.icv.model.request.TodoCreateRequest;
import com.taskservice.icv.model.request.TodoUpdateRequest;
import com.taskservice.icv.persistence.converter.TodoConverter;
import com.taskservice.icv.persistence.entity.TodoEntity;
import com.taskservice.icv.persistence.service.TodoPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoPersistenceService todoPersistenceService;
    private final TodoConverter todoConverter;

    public TodoDto getById(Long id) {
        final TodoEntity entity = todoPersistenceService.getById(id);
        return todoConverter.mapToDto(entity);
    }

    public List<TodoDto> getAll() {
        final List<TodoEntity> entities = todoPersistenceService.findAll();
        return todoConverter.mapToDtoList(entities);
    }

    public TodoDto create(TodoCreateRequest request) {
        final TodoEntity entity = todoConverter.mapToEntity(request);
        todoPersistenceService.save(entity);
        return todoConverter.mapToDto(entity);
    }

    public TodoDto update(Long id, TodoUpdateRequest request) {
        final TodoEntity entity = todoPersistenceService.getById(id);
        if (StringUtils.hasText(request.getDescription())) {
            entity.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getStatus())) {
            entity.setStatus(request.getStatus());
        }
        todoPersistenceService.save(entity);
        return todoConverter.mapToDto(entity);
    }

    public void deleteById(Long id) {
        todoPersistenceService.deleteById(id);
    }
}
