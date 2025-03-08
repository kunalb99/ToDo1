package com.wiom.todo.service;

import com.wiom.todo.dto.TaskDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Page<TaskDTO> getAllTasks(int page, int size);
    Optional<TaskDTO> getTaskById(Long id);
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    TaskDTO updateTaskStatus(Long id, boolean completed);
    void deleteTask(Long id);
}
