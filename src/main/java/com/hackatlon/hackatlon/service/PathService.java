package com.hackatlon.hackatlon.service;

import com.hackatlon.hackatlon.exception.ResourceNotFoundException;
import com.hackatlon.hackatlon.model.Path;
import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.payload.PathResponse;
import com.hackatlon.hackatlon.payload.TaskResponse;
import com.hackatlon.hackatlon.repository.PathRepository;
import com.hackatlon.hackatlon.repository.UserRepository;
import com.hackatlon.hackatlon.util.ModelMapper;
import com.hackatlon.hackatlon.util.ValidatePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PathService {

    public PathService(UserRepository userRepository, PathRepository pathRepository) {
        this.userRepository = userRepository;

        this.pathRepository = pathRepository;
    }
    private final UserRepository userRepository;
    private final PathRepository pathRepository;

    public PagedResponse<PathResponse> getAllPaths(int page, int size) {
        ValidatePageUtil.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "title");
        Page<Path> paths = pathRepository.findAll(pageable);

        if(paths.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), paths.getNumber(), paths.getSize(), paths.getTotalElements(), paths.getTotalPages(), paths.isLast());
        }

        List<PathResponse> pathResponses = paths.map(path -> ModelMapper.mapPathToPathResponse(path)).getContent();

        return new PagedResponse<>(pathResponses, paths.getNumber(), paths.getSize(), paths.getTotalElements(), paths.getTotalPages(), paths.isLast());
    }

    public PathResponse findById(long pathId) {
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new ResourceNotFoundException("Path", "id", pathId));

        return ModelMapper.mapPathToPathResponse(path);
    }

}
