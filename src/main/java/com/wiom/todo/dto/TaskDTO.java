package com.wiom.todo.dto;

import java.util.List;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private List<SubTaskDTO> subTasks;

    public TaskDTO() {}

    public TaskDTO(Long id, String title, String description, boolean completed, List<SubTaskDTO> subTasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.subTasks = subTasks;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public List<SubTaskDTO> getSubTasks() {
        return subTasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setSubTasks(List<SubTaskDTO> subTasks) {
        this.subTasks = subTasks;
    }
}
