package com.hackatlon.hackatlon.util;


import com.hackatlon.hackatlon.model.DoneTask;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.DoneTaskResponse;
import com.hackatlon.hackatlon.payload.UserSummary;

public class ModelMapper {

    public static DoneTaskResponse mapDoneTaskToDoneTaskResponse(DoneTask doneTask, User creator) {
        DoneTaskResponse doneTaskResponse = new DoneTaskResponse();

        UserSummary userSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        doneTaskResponse.setCreatedAt(doneTask.getCreatedAt());
        doneTaskResponse.setCreatedBy(userSummary);
        doneTaskResponse.setId(doneTask.getId());
        doneTaskResponse.setTask(doneTask.getTask());

        return doneTaskResponse;
    }

}