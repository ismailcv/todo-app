package com.taskservice.icv.controller;

import com.taskservice.icv.model.dto.TodoDto;
import com.taskservice.icv.model.request.TodoCreateRequest;
import com.taskservice.icv.model.request.TodoUpdateRequest;
import com.taskservice.icv.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDto create(@RequestBody TodoCreateRequest request) {
        return todoService.create(request);
    }

    @PutMapping("/{id}")
    public TodoDto update(@PathVariable Long id,
                          @RequestBody TodoUpdateRequest request) {
        return todoService.update(id, request);
    }

    @GetMapping
    public List<TodoDto> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public TodoDto getById(@PathVariable Long id) {
        return todoService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        todoService.deleteById(id);
    }
}
