package com.taskservice.icv.model.dto;

import com.taskservice.icv.model.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Long id;
    private Long userId;
    private String description;
    private TodoStatus status;
}


