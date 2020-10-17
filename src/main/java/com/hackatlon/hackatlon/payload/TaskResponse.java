package com.hackatlon.hackatlon.payload;

import com.hackatlon.hackatlon.service.UserService;

import java.time.Instant;

public class TaskResponse {

    private Long id;
    private String title;


    public TaskResponse() {}

    public TaskResponse(Long id, String title) {
        this.id = id;
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
