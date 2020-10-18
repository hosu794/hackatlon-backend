package com.hackatlon.hackatlon.controller;

import com.hackatlon.hackatlon.payload.PagedResponse;
import com.hackatlon.hackatlon.payload.PathResponse;
import com.hackatlon.hackatlon.service.PathService;
import com.hackatlon.hackatlon.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/path")
public class PathController {


    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    private final PathService pathService;


    @GetMapping
    public PagedResponse<PathResponse> getAllPaths(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pathService.getAllPaths(page, size);
    }

}
