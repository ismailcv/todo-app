package com.taskservice.icv.persistence.service;

import com.taskservice.icv.advice.exception.NoSuchTodoException;
import com.taskservice.icv.model.enums.TodoStatus;
import com.taskservice.icv.persistence.entity.TodoEntity;
import com.taskservice.icv.persistence.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoPersistenceService {

    private final TodoRepository todoRepository;

    public TodoEntity getById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(NoSuchTodoException::new);
    }

    public List<TodoEntity> findAll() {
        return todoRepository.findAllByStatusNot(TodoStatus.REMOVED);
    }

    public void save(TodoEntity todoEntity) {
        todoRepository.save(todoEntity);
    }

    public void deleteById(Long id) {
        TodoEntity todo = todoRepository.findById(id).orElseThrow(NoSuchTodoException::new);
        todo.setStatus(TodoStatus.REMOVED);
        todoRepository.save(todo);
    }
}
