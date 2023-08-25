package com.taskservice.icv.model.request;

import com.sun.istack.NotNull;
import com.taskservice.icv.model.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoCreateRequest {

    @NotNull
    private long userId;
    private String description;
    private TodoStatus status = TodoStatus.TODO;
}
