package com.wiom.todo.controller;

import com.wiom.todo.dto.SubTaskDTO;
import com.wiom.todo.service.SubTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subTasks")
public class SubTaskController {
    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @GetMapping("/tasks/{taskId}")
    public List<SubTaskDTO> getAllSubTasksByTask(@PathVariable Long taskId) {
        return subTaskService.getAllSubTasks(taskId);
    }

    @GetMapping("/{subTaskId}")
    public ResponseEntity<SubTaskDTO> getSubTaskById(@PathVariable Long subTaskId) {
        return subTaskService.getSubTaskById(subTaskId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tasks/{taskId}")
    public SubTaskDTO createSubTask(@PathVariable Long taskId, @RequestBody SubTaskDTO subTaskDTO) {
        return subTaskService.createSubTask(taskId, subTaskDTO);
    }

    @PutMapping("/{subTaskId}")
    public ResponseEntity<SubTaskDTO> updateSubTask(@PathVariable Long subTaskId, @RequestBody SubTaskDTO subTaskDTO) {
        return ResponseEntity.ok(subTaskService.updateSubTask(subTaskId, subTaskDTO));
    }

    @PatchMapping("/{subTaskId}/status")
    public ResponseEntity<SubTaskDTO> updateSubTaskStatus(@PathVariable Long subTaskId, @RequestParam boolean completed) {
        return ResponseEntity.ok(subTaskService.updateSubTaskStatus(subTaskId, completed));
    }

    @DeleteMapping("/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable Long subTaskId) {
        subTaskService.deleteSubTask(subTaskId);
        return ResponseEntity.noContent().build();
    }
}
