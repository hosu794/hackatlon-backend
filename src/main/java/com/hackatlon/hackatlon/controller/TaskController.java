package com.hackatlon.hackatlon.controller;

import com.hackatlon.hackatlon.model.Task;
import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.payload.TaskResponse;
import com.hackatlon.hackatlon.service.TaskService;
import com.hackatlon.hackatlon.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/task")
public class TaskController {

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private final TaskService taskService;


    @GetMapping
    public PagedResponse<TaskResponse> getAllTasks(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return taskService.getAllTasks(page, size);
    }

    @GetMapping("/path/{pathId}")
    public PagedResponse<TaskResponse> getByPath(@PathVariable Long pathId,  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return taskService.getAllByPathId(pathId, page, size);
    }

}
