package com.hackatlon.hackatlon.model;

import com.hackatlon.hackatlon.model.audit.UserDateAudit;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "done_tasks")
public class DoneTask extends UserDateAudit {

    @Id
    @Column(name = "done_task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="task_id", referencedColumnName = "task_id", nullable = false)
    @NotNull
    private Task task;

    @ManyToOne
    @JoinColumn(name = "path_id", referencedColumnName = "path_id", nullable = false)
    @NotNull
    private Path path;

    public DoneTask() {}

    public DoneTask(Long id, @NotNull Task task, @NotNull Path path) {
        this.id = id;
        this.task = task;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
