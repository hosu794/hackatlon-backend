package com.hackatlon.hackatlon.service;


import com.hackatlon.hackatlon.exception.BadRequestException;
import com.hackatlon.hackatlon.exception.ResourceNotFoundException;
import com.hackatlon.hackatlon.model.DoneTask;
import com.hackatlon.hackatlon.model.Task;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.ApiResponse;
import com.hackatlon.hackatlon.payload.DoneTaskResponse;
import com.hackatlon.hackatlon.payload.LevelResponse;
import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.repository.DoneTaskRepository;
import com.hackatlon.hackatlon.repository.PathRepository;
import com.hackatlon.hackatlon.repository.TaskRepository;
import com.hackatlon.hackatlon.repository.UserRepository;
import com.hackatlon.hackatlon.security.UserPrincipal;
import com.hackatlon.hackatlon.util.ModelMapper;
import com.hackatlon.hackatlon.util.ValidatePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoneTaskServiceImpl {

    private final DoneTaskRepository doneTaskRepository;
    private final TaskRepository taskRepository;
    private final PathRepository pathRepository;
    private final UserRepository userRepository;

    public DoneTaskServiceImpl(DoneTaskRepository doneTaskRepository, TaskRepository taskRepository, PathRepository
            pathRepository, UserRepository userRepository) {
        this.doneTaskRepository = doneTaskRepository;
        this.taskRepository = taskRepository;
        this.pathRepository = pathRepository;
        this.userRepository = userRepository;

    }

    public PagedResponse<DoneTaskResponse> getDoneTasksByCreatedBy(UserPrincipal currentUser, int page, int size) {
        ValidatePageUtil.validatePageNumberAndSize(page, size);

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "user", currentUser.getId()));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<DoneTask> doneTasks = doneTaskRepository.findByCreatedBy(user.getId(), pageable);

        if(doneTasks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), doneTasks.getNumber(), doneTasks.getSize(), doneTasks.getTotalElements(), doneTasks.getTotalPages(), doneTasks.isLast());
        }

        Map<Long, User> creatorMap = getCreatorsIdsAndCreatorOfDoneTask(doneTasks.getContent());



        List<DoneTaskResponse> doneTaskResponses = doneTasks.map(doneTask -> ModelMapper.mapDoneTaskToDoneTaskResponse(doneTask, creatorMap.get(doneTask.getCreatedBy()))).getContent();

        return new PagedResponse<>(doneTaskResponses, doneTasks.getNumber(), doneTasks.getSize(), doneTasks.getTotalElements(), doneTasks.getTotalPages(), doneTasks.isLast());
    }

    public LevelResponse getLevel(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        List<DoneTask> doneTasks = doneTaskRepository.findByCreatedBy(user.getId());

        float currentLevel = doneTasks.size() / 10l;
        long roundedLevel = Math.round(currentLevel);

        return new LevelResponse(roundedLevel);
    }



    public DoneTask createDoneTask(UserPrincipal currentUser, Long taskId) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        DoneTask doneTask = new DoneTask();
        doneTask.setTask(task);

        return doneTaskRepository.save(doneTask);
    }

    public ResponseEntity<?> deleteDoneTask(UserPrincipal currentUser, Long doneTaskId) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        DoneTask doneTask = doneTaskRepository.findById(doneTaskId).orElseThrow(() -> new ResourceNotFoundException("DoneTask", "id", doneTaskId));

        User doneTaskCreator = userRepository.findById(doneTask.getCreatedBy()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        if(user.getId() == doneTaskCreator.getId()) {
            doneTaskRepository.delete(doneTask);
            return ResponseEntity.ok(new ApiResponse(true, "Uncheck a task successfully"));
        } else {
            throw new BadRequestException("You're not a owner of the doneTask");
        }


    }

    public Map<Long, User> getCreatorsIdsAndCreatorOfDoneTask(List<DoneTask> doneTasks) {


        List<Long> creatorIds = doneTasks.stream()
                .map(DoneTask::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);

        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;

    }
}

