package com.wiom.todo.service;

import com.wiom.todo.dto.SubTaskDTO;
import com.wiom.todo.entity.SubTask;
import com.wiom.todo.entity.Task;
import com.wiom.todo.repository.SubTaskRepository;
import com.wiom.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public SubTaskServiceImpl(SubTaskRepository subTaskRepository, TaskRepository taskRepository) {
        this.subTaskRepository = subTaskRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<SubTaskDTO> getAllSubTasks(Long taskId) {
        return subTaskRepository.findByTaskId(taskId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubTaskDTO> getSubTaskById(Long subTaskId) {
        return subTaskRepository.findById(subTaskId)
                .map(this::convertToDTO);
    }

    @Override
    public SubTaskDTO createSubTask(Long taskId, SubTaskDTO subTaskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        SubTask subTask = convertToEntity(subTaskDTO);
        subTask.setTask(task);
        return convertToDTO(subTaskRepository.save(subTask));
    }

    @Override
    public SubTaskDTO updateSubTask(Long subTaskId, SubTaskDTO subTaskDTO) {
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new RuntimeException("SubTask not found"));

        subTask.setTitle(subTaskDTO.getTitle());
        subTask.setCompleted(subTaskDTO.isCompleted());

        return convertToDTO(subTaskRepository.save(subTask));
    }

    @Override
    public SubTaskDTO updateSubTaskStatus(Long subTaskId, boolean completed) {
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new RuntimeException("SubTask not found"));

        subTask.setCompleted(completed);
        return convertToDTO(subTaskRepository.save(subTask));
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        subTaskRepository.deleteById(subTaskId);
    }

    private SubTaskDTO convertToDTO(SubTask subTask) {
        return new SubTaskDTO(subTask.getId(), subTask.getTitle(), subTask.isCompleted(), subTask.getTask().getId());
    }

    private SubTask convertToEntity(SubTaskDTO subTaskDTO) {
        SubTask subTask = new SubTask();
        subTask.setTitle(subTaskDTO.getTitle());
        subTask.setCompleted(subTaskDTO.isCompleted());
        return subTask;
    }
}
