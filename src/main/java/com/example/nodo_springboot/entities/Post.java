package com.example.nodo_springboot.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "POSTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Post {
    @EmbeddedId
    private PostId id;

    private String title;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;
}
