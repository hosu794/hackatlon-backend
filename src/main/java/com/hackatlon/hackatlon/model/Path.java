package com.hackatlon.hackatlon.model;

import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "paths", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "title"
        })
})
public class Path {

    @Id
    @Column(name = "path_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String imageUrl;

    public Path() {}

    public Path(Long id, @NotBlank String title) {
        this.id = id;
        this.title = title;
    }

    public Path(Long id, @NotBlank String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
}
