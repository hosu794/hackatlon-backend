package com.hackatlon.hackatlon.payload;

public class DoneTaskRequest {

    private Long taskId;

    public DoneTaskRequest() {}

    public DoneTaskRequest(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
