package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "COMMENTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // Set fetch to LAZY
    @JoinColumns({
            @JoinColumn(name = "post_id", referencedColumnName = "postId"),
            @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    })
    private Post post; // Comment should not cascade to post; it's child/owner of relationship.
}
