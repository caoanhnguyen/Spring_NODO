package com.example.nodo_springboot.repository;

import com.example.nodo_springboot.entities.Post;
import com.example.nodo_springboot.entities.PostId;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {

    @Query("select p.id from Post p")
    Page<PostId> getAllPostIds();

    @Query("select distinct p from Post p where p.id in :ids")
    List<Post> findAll(List<PostId> ids);

}
