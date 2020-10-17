package com.hackatlon.hackatlon.repository;

import com.hackatlon.hackatlon.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByPathId(Long pathId, Pageable pageable);
    List<Task> findByPathId(Long pathI);
}
