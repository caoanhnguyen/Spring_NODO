package com.example.nodo_springboot.entities;

import jakarta.persistence.CascadeType;
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

    // Post owns comments: cascade all so when post is created/removed, comments follow. Use orphanRemoval true so
    // removing comment from collection deletes it.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
}
