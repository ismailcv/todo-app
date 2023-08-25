package com.taskservice.icv.persistence.entity;

import com.taskservice.icv.model.enums.TodoStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
public class TodoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private TodoStatus status;
}
