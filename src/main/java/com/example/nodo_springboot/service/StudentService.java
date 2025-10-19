package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.*;
import com.example.nodo_springboot.projection.StudentNameEmailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService {
    StudentResponseDTO create(StudentRequestDTO studentDTO);
    Page<StudentResponseDTO> findAll(Pageable pageable);
    Optional<StudentResponseDTO> findById(Long id);
    StudentResponseDTO update(Long id, StudentRequestDTO studentDTO);
    void delete(Long id);
    PageResponse<StudentResponseDTO> searchDynamic(StudentSearchDTO dto, Pageable pageable);
    PageResponse<StudentResponseDTO> searchByJPQL(StudentSearchDTO dto, Pageable pageable);
    Page<StudentResponseDTO> searchByNative(StudentSearchDTO dto, Pageable pageable);
    boolean existsByEmail(String email);

    // Projection-based methods
    Page<StudentNameEmailProjection> findByFullNameProjection(String name, Pageable pageable);
    Page<StudentResponseDTO> findByFullNameProjectionAsDto(String name, Pageable pageable);

//
//    List<Map<String, Object>> searchDynamicFieldsJPQL(StudentDynamicSearchDTO dto);
//    List<Map<String, Object>> searchDynamicFieldsNative(StudentDynamicSearchDTO dto);
}