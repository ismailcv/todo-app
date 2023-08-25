package com.taskservice.icv.model.request;

import com.taskservice.icv.model.enums.TodoStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoUpdateRequest {

    private String description;
    private TodoStatus status;
}
