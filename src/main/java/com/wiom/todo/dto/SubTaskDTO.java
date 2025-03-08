package com.wiom.todo.dto;

public class SubTaskDTO {
    private Long id;
    private String title;
    private boolean completed;
    private Long taskId;

    public SubTaskDTO() {}

    public SubTaskDTO(Long id, String title, boolean completed, Long taskId) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.taskId = taskId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
}
