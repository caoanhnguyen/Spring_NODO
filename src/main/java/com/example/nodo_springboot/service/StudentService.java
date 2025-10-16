package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    StudentDTO create(StudentDTO studentDTO);
    List<StudentDTO> findAll();
    Optional<StudentDTO> findById(Long id);
    StudentDTO update(Long id, StudentDTO studentDTO);
    void delete(Long id);
    Page<StudentDTO> search(String keyword, Pageable pageable);
    boolean existsByEmail(String email);
}