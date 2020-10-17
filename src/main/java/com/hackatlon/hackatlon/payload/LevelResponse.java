package com.hackatlon.hackatlon.payload;

public class LevelResponse {

    private Long level;

    public LevelResponse() {}

    public LevelResponse(Long level) {
        this.level = level;
    }
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
