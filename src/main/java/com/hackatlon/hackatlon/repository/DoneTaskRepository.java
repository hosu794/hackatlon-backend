package com.hackatlon.hackatlon.repository;

import com.hackatlon.hackatlon.model.DoneTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoneTaskRepository extends JpaRepository<DoneTask, Long> {

    Page<DoneTask> findByCreatedBy(Long userId, Pageable pageable);

    Page<DoneTask> findByPathIdAndCreatedBy(Long pathId, Long userId, Pageable pageable);
    List<DoneTask> findByPathIdAndCreatedBy(Long pathId, Long userId);
    List<DoneTask> findByCreatedBy(Long userId);

    Optional<DoneTask> findById(Long doneTaskId);

}
