package com.hackatlon.hackatlon.controller;

import com.hackatlon.hackatlon.model.DoneTask;
import com.hackatlon.hackatlon.payload.DoneTaskResponse;
import com.hackatlon.hackatlon.payload.LevelResponse;
import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.security.CurrentUser;
import com.hackatlon.hackatlon.security.UserPrincipal;
import com.hackatlon.hackatlon.service.DoneTaskService;
import com.hackatlon.hackatlon.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/done/task")
public class DoneTaskController {


    public DoneTaskController(DoneTaskService doneTaskService) {
        this.doneTaskService = doneTaskService;
    }
    private final DoneTaskService doneTaskService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<DoneTaskResponse> getAllDoneTaskByCreatedBy(@CurrentUser UserPrincipal currentUser,
                                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return doneTaskService.getDoneTasksByCreatedBy(currentUser, page, size);
    }

    @GetMapping("/level")
    @PreAuthorize("hasRole('USER')")
    public LevelResponse getLevel(@CurrentUser UserPrincipal currentUser) {
        return doneTaskService.getLevel(currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public DoneTask createDoneTask(@CurrentUser UserPrincipal currentUser, Long taskId) {
        return doneTaskService.createDoneTask(currentUser, taskId);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteDoneTask(@CurrentUser UserPrincipal currentUser, Long doneTaskId) {
        return doneTaskService.deleteDoneTask(currentUser, doneTaskId);
    }


}
