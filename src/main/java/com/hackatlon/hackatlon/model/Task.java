package com.hackatlon.hackatlon.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "title"
        })
})
public class Task {


    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne
    @JoinColumn(name = "path_id", referencedColumnName = "path_id", nullable = false)
    @NotNull
    private Path path;



    public Task(Long id, @NotBlank String title, @NotNull Path path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public Task() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
