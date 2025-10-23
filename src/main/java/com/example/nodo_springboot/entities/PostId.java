package com.example.nodo_springboot.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class PostId implements Serializable {
    private String postId;
    private String categoryId;

    // Constructors, getters, setters, equals, and hashCode methods
}
