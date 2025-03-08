package com.wiom.todo.service;

import com.wiom.todo.dto.SubTaskDTO;
import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.entity.SubTask;
import com.wiom.todo.entity.Task;
import com.wiom.todo.repository.TaskRepository;
import com.wiom.todo.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<TaskDTO> getAllTasks(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size)).map(this::convertToDTO);
    }

    @Override
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        return convertToDTO(taskRepository.save(task));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        return convertToDTO(taskRepository.save(task));
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, boolean completed) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(completed);
        return convertToDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getSubTasks() != null
                        ? task.getSubTasks().stream().map(this::convertSubTaskToDTO).collect(Collectors.toList())
                        : null
        );
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.isCompleted());

        if (taskDTO.getSubTasks() != null) {
            List<SubTask> subTasks = taskDTO.getSubTasks().stream()
                    .map(subTaskDTO -> convertSubTaskToEntity(subTaskDTO, task)) // Corrected lambda
                    .collect(Collectors.toList());
            task.setSubTasks(subTasks);
        }

        return task;
    }


    private SubTaskDTO convertSubTaskToDTO(SubTask subTask) {
        return new SubTaskDTO(
                subTask.getId(),
                subTask.getTitle(),
                subTask.isCompleted(),
                subTask.getTask().getId() // Ensure taskId is correctly set
        );
    }

    private SubTask convertSubTaskToEntity(SubTaskDTO subTaskDTO, Task task) {
        return new SubTask(
                subTaskDTO.getId(), // Include ID to prevent duplicate creation
                subTaskDTO.getTitle(),
                subTaskDTO.isCompleted(),
                task // Associate subtask with its parent task
        );
    }
}
