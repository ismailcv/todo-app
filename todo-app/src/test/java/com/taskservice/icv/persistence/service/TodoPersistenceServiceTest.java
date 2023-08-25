package com.taskservice.icv.persistence.service;

import com.taskservice.icv.advice.exception.NoSuchTodoException;
import com.taskservice.icv.model.enums.TodoStatus;
import com.taskservice.icv.persistence.entity.TodoEntity;
import com.taskservice.icv.persistence.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoPersistenceServiceTest {

    @InjectMocks
    private TodoPersistenceService todoPersistenceService;

    @Mock
    private TodoRepository todoRepository;

    @Test
    void it_should_get_todo_by_id() {
        // Given
        Long todoId = 1L;
        TodoEntity todoEntity = TodoEntity.builder()
                .id(todoId)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todoEntity));

        // When
        TodoEntity result = todoPersistenceService.getById(todoId);

        // Then
        assertEquals(todoId, result.getId());
        assertEquals("desc", result.getDescription());
        assertEquals(TodoStatus.TODO, result.getStatus());
    }

    @Test
    void it_should_throw_exception_when_todo_not_found() {
        // Given
        Long nonExistentTodoId = 999L;

        when(todoRepository.findById(nonExistentTodoId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NoSuchTodoException.class, () -> todoPersistenceService.deleteById(nonExistentTodoId));

        // Ensure that todoRepository.save() is never called
        verify(todoRepository, never()).save(any());
    }

    @Test
    void it_should_find_all_todos_except_removed() {
        // Given
        List<TodoEntity> expectedTodoEntities = Arrays.asList(
                TodoEntity.builder().id(1L).userId(1L).description("desc1").status(TodoStatus.TODO).build(),
                TodoEntity.builder().id(2L).userId(2L).description("desc2").status(TodoStatus.ARCHIVED).build()
        );

        when(todoRepository.findAllByStatusNot(TodoStatus.REMOVED)).thenReturn(expectedTodoEntities);

        // When
        List<TodoEntity> result = todoPersistenceService.findAll();

        // Then
        assertEquals(expectedTodoEntities.size(), result.size());
        for (int i = 0; i < expectedTodoEntities.size(); i++) {
            assertEquals(expectedTodoEntities.get(i).getId(), result.get(i).getId());
            assertEquals(expectedTodoEntities.get(i).getUserId(), result.get(i).getUserId());
            assertEquals(expectedTodoEntities.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(expectedTodoEntities.get(i).getStatus(), result.get(i).getStatus());
        }
    }

    @Test
    void it_should_save_todo_entity() {
        // Given
        TodoEntity todoEntity = TodoEntity.builder()
                .id(1L)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        // When
        todoPersistenceService.save(todoEntity);

        // Then
        verify(todoRepository).save(todoEntity);
    }

    @Test
    void it_should_delete_todo_by_id() {
        // Given
        Long todoId = 1L;
        TodoEntity todoEntity = TodoEntity.builder()
                .id(todoId)
                .userId(1L)
                .description("desc")
                .status(TodoStatus.TODO)
                .build();

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todoEntity));

        // When
        todoPersistenceService.deleteById(todoId);

        // Then
        verify(todoRepository).save(todoEntity);
    }
}