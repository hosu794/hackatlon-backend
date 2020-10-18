package com.hackatlon.hackatlon.util;


import com.hackatlon.hackatlon.model.DoneTask;
import com.hackatlon.hackatlon.model.Path;
import com.hackatlon.hackatlon.model.Task;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.DoneTaskResponse;
import com.hackatlon.hackatlon.payload.PathResponse;
import com.hackatlon.hackatlon.payload.TaskResponse;
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

    public static TaskResponse mapTaskToTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());

        return taskResponse;
    }

    public static PathResponse mapPathToPathResponse(Path path) {
        PathResponse pathResponse = new PathResponse();
        pathResponse.setId(path.getId());
        pathResponse.setTitle(path.getTitle());
        return pathResponse;
    }


}