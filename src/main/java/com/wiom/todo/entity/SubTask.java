package com.wiom.todo.entity;

import jakarta.persistence.*;

@Entity
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public SubTask() {}

    public SubTask(Long id, String title, boolean completed, Task task) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.task = task;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
}
