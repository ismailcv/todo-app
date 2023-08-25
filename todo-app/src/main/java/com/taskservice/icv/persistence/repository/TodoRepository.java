package com.taskservice.icv.persistence.repository;

import com.taskservice.icv.model.enums.TodoStatus;
import com.taskservice.icv.persistence.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findAllByStatusNot(TodoStatus status);
}
