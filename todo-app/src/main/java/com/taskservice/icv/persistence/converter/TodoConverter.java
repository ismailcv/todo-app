package com.taskservice.icv.persistence.converter;

import com.taskservice.icv.model.dto.TodoDto;
import com.taskservice.icv.model.request.TodoCreateRequest;
import com.taskservice.icv.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoConverter {

    public TodoEntity mapToEntity(TodoCreateRequest request) {
        return TodoEntity.builder()
                .userId(request.getUserId())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
    }

    public TodoDto mapToDto(TodoEntity entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    public List<TodoDto> mapToDtoList(List<TodoEntity> entities) {
        return entities.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
