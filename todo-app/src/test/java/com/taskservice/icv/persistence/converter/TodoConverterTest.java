package com.taskservice.icv.persistence.converter;

import com.taskservice.icv.model.dto.TodoDto;
import com.taskservice.icv.model.enums.TodoStatus;
import com.taskservice.icv.model.request.TodoCreateRequest;
import com.taskservice.icv.persistence.entity.TodoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoConverterTest {

    @InjectMocks
    private TodoConverter todoConverter;

    @Test
    void it_should_map_to_entity() {
        // Given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                .userId(1L)
                .description("description")
                .status(TodoStatus.TODO)
                .build();

        // When
        TodoEntity todoEntity = todoConverter.mapToEntity(todoCreateRequest);

        // Then
        assertEquals(1L, todoEntity.getUserId());
        assertEquals("description", todoEntity.getDescription());
        assertEquals(TodoStatus.TODO, todoEntity.getStatus());
    }

    @Test
    void it_should_map_to_dto() {
        // Given
        TodoEntity todoEntity = TodoEntity.builder()
                .id(1L)
                .userId(1L)
                .description("description")
                .status(TodoStatus.TODO)
                .build();

        // When
        TodoDto todoDto = todoConverter.mapToDto(todoEntity);

        // Then
        assertEquals(1L, todoDto.getId());
        assertEquals(1L, todoDto.getUserId());
        assertEquals("description", todoDto.getDescription());
        assertEquals(TodoStatus.TODO, todoDto.getStatus());
    }

    @Test
    void it_should_map_to_dto_list() {
        // Given
        List<TodoEntity> todoEntities = Arrays.asList(
                TodoEntity.builder().id(1L).userId(1L).description("desc1").status(TodoStatus.TODO).build(),
                TodoEntity.builder().id(2L).userId(2L).description("desc2").status(TodoStatus.REMOVED).build()
        );

        // When
        List<TodoDto> todoDto = todoConverter.mapToDtoList(todoEntities);

        // Then
        assertEquals(2, todoDto.size());

        assertEquals(1L, todoDto.get(0).getId());
        assertEquals(1L, todoDto.get(0).getUserId());
        assertEquals("desc1", todoDto.get(0).getDescription());
        assertEquals(TodoStatus.TODO, todoDto.get(0).getStatus());

        assertEquals(2L, todoDto.get(1).getId());
        assertEquals(2L, todoDto.get(1).getUserId());
        assertEquals("desc2", todoDto.get(1).getDescription());
        assertEquals(TodoStatus.REMOVED, todoDto.get(1).getStatus());
    }
}