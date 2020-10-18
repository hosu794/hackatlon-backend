package com.hackatlon.hackatlon.payload;

public class PathResponse {

    private Long id;
    private String title;

    public PathResponse() {}

    public PathResponse(Long id, String title) {
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
