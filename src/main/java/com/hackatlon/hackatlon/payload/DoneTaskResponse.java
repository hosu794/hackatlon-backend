package com.hackatlon.hackatlon.payload;

import com.hackatlon.hackatlon.model.Task;

import java.time.Instant;

public class DoneTaskResponse {

    private Long id;

    private Task task;

    private UserSummary createdBy;

    private Instant createdAt;

    public DoneTaskResponse() {}

    public DoneTaskResponse(Long id, Task task, UserSummary createdBy, Instant createdAt) {
        this.id = id;
        this.task = task;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
