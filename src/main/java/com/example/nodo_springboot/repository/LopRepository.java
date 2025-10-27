package com.example.nodo_springboot.repository;

import com.example.nodo_springboot.entities.Lop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {
}

