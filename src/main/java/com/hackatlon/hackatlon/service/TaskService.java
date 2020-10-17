package com.hackatlon.hackatlon.service;

import com.hackatlon.hackatlon.exception.ResourceNotFoundException;
import com.hackatlon.hackatlon.model.DoneTask;
import com.hackatlon.hackatlon.model.Task;
import com.hackatlon.hackatlon.model.User;
import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.payload.TaskResponse;
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
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {


    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public PagedResponse<TaskResponse> getAllTasks(int page, int size) {
        ValidatePageUtil.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "title");
        Page<Task> tasks = taskRepository.findAll(pageable);

        if(tasks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), tasks.getNumber(), tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
        }

        List<TaskResponse> taskResponses = tasks.map(task -> ModelMapper.mapTaskToTaskResponse(task)).getContent();



        return new PagedResponse<>(taskResponses, tasks.getNumber(), tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());

    }

    public PagedResponse<TaskResponse> getAllByPathId(Long pathId, int page, int size) {
        ValidatePageUtil.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "title");
        Page<Task> tasks = taskRepository.findByPathId(pathId, pageable);

        if(tasks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), tasks.getNumber(), tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
        }

        List<TaskResponse> taskResponses = tasks.map(task -> ModelMapper.mapTaskToTaskResponse(task)).getContent();

        return new PagedResponse<>(taskResponses, tasks.getNumber(), tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
    }






}
