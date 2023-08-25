package com.taskservice.icv.service;

import com.taskservice.icv.model.dto.TodoDto;
import com.taskservice.icv.model.enums.TodoStatus;
import com.taskservice.icv.model.request.TodoCreateRequest;
import com.taskservice.icv.model.request.TodoUpdateRequest;
import com.taskservice.icv.persistence.converter.TodoConverter;
import com.taskservice.icv.persistence.entity.TodoEntity;
import com.taskservice.icv.persistence.service.TodoPersistenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoPersistenceService todoPersistenceService;

    @Mock
    private TodoConverter todoConverter;

    @Test
    void it_should_get_by_id() {
        // Given
        TodoEntity todoEntity = new TodoEntity();
        when(todoPersistenceService.getById(1L)).thenReturn(todoEntity);

        TodoDto todoDto = new TodoDto();
        when(todoConverter.mapToDto(todoEntity)).thenReturn(todoDto);

        // When
        TodoDto responseTodoDto = todoService.getById(1L);

        // Then
        assertEquals(responseTodoDto, todoDto);
        verify(todoPersistenceService).getById(1L);
        verify(todoConverter).mapToDto(todoEntity);
    }

    @Test
    void it_should_get_all_todos() {
        // Given
        List<TodoEntity> todoEntities = Arrays.asList(
                TodoEntity.builder().id(1L).userId(1L).description("desc1").status(TodoStatus.TODO).build(),
                TodoEntity.builder().id(2L).userId(2L).description("desc2").status(TodoStatus.REMOVED).build()
        );

        List<TodoDto> todoDto = Arrays.asList(
                TodoDto.builder().id(1L).userId(1L).description("desc1").status(TodoStatus.TODO).build(),
                TodoDto.builder().id(2L).userId(2L).description("desc2").status(TodoStatus.REMOVED).build()
        );

        when(todoPersistenceService.findAll()).thenReturn(todoEntities);
        when(todoConverter.mapToDtoList(todoEntities)).thenReturn(todoDto);

        // When
        List<TodoDto> result = todoService.getAll();

        // Then
        assertEquals(todoDto.size(), result.size());
        for (int i = 0; i < todoDto.size(); i++) {
            assertEquals(todoDto.get(i).getId(), result.get(i).getId());
            assertEquals(todoDto.get(i).getUserId(), result.get(i).getUserId());
            assertEquals(todoDto.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(todoDto.get(i).getStatus(), result.get(i).getStatus());
        }
    }

    @Test
    void it_should_create_todo() {
        // Given
        TodoCreateRequest createRequest = TodoCreateRequest.builder()
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        TodoEntity todoEntity = TodoEntity.builder()
                .id(1L)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        TodoDto todoDto = TodoDto.builder()
                .id(1L)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        when(todoConverter.mapToEntity(createRequest)).thenReturn(todoEntity);
        when(todoConverter.mapToDto(todoEntity)).thenReturn(todoDto);

        // When
        TodoDto result = todoService.create(createRequest);

        // Then
        assertEquals(todoDto.getId(), result.getId());
        assertEquals(todoDto.getUserId(), result.getUserId());
        assertEquals(todoDto.getDescription(), result.getDescription());
        assertEquals(todoDto.getStatus(), result.getStatus());

        verify(todoPersistenceService).save(todoEntity);
    }

    @Test
    void it_should_update_description() {
        // Given
        Long todoId = 1L;

        TodoUpdateRequest updateRequest = TodoUpdateRequest.builder()
                .description("newDesc")
                .status(null)
                .build();

        TodoEntity todoEntity = TodoEntity.builder()
                .id(todoId)
                .userId(1L)
                .description("oldDesc")
                .status(TodoStatus.TODO)
                .build();

        when(todoPersistenceService.getById(todoId)).thenReturn(todoEntity);

        // When
        TodoDto result = todoService.update(todoId, updateRequest);

        // Then
        assertEquals("newDesc", todoEntity.getDescription());
        verify(todoPersistenceService).save(todoEntity);
    }

    @Test
    void it_should_update_status() {
        // Given
        Long todoId = 1L;

        TodoUpdateRequest updateRequest = TodoUpdateRequest.builder()
                .description(null)
                .status(TodoStatus.ARCHIVED)
                .build();

        TodoEntity todoEntity = TodoEntity.builder()
                .id(todoId)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        when(todoPersistenceService.getById(todoId)).thenReturn(todoEntity);

        // When
        TodoDto result = todoService.update(todoId, updateRequest);

        // Then
        assertEquals(TodoStatus.ARCHIVED, todoEntity.getStatus());
        verify(todoPersistenceService).save(todoEntity);
    }

    @Test
    void it_should_update_description_and_status() {
        // Given
        Long todoId = 1L;

        TodoUpdateRequest updateRequest = TodoUpdateRequest.builder()
                .description("newDesc")
                .status(TodoStatus.ARCHIVED)
                .build();

        TodoEntity todoEntity = TodoEntity.builder()
                .id(todoId)
                .userId(1L)
                .description("oldDesc")
                .status(TodoStatus.TODO)
                .build();

        when(todoPersistenceService.getById(todoId)).thenReturn(todoEntity);

        // When
        TodoDto result = todoService.update(todoId, updateRequest);

        // Then
        assertEquals("newDesc", todoEntity.getDescription());
        assertEquals(TodoStatus.ARCHIVED, todoEntity.getStatus());
        verify(todoPersistenceService).save(todoEntity);
    }

    @Test
    void it_should_delete_todo_by_id() {
        // Given
        Long todoId = 1L;

        // When
        todoService.deleteById(todoId);

        // Then
        verify(todoPersistenceService).deleteById(todoId);
    }
}