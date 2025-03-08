package com.wiom.todo.service;

import com.wiom.todo.dto.SubTaskDTO;

import java.util.List;
import java.util.Optional;

public interface SubTaskService {
    List<SubTaskDTO> getAllSubTasks(Long taskId);
    Optional<SubTaskDTO> getSubTaskById(Long subTaskId);
    SubTaskDTO createSubTask(Long taskId, SubTaskDTO subTaskDTO);
    SubTaskDTO updateSubTask(Long subTaskId, SubTaskDTO subTaskDTO);
    SubTaskDTO updateSubTaskStatus(Long subTaskId, boolean completed);
    void deleteSubTask(Long subTaskId);
}
