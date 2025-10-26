package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.PageResponseDTO;
import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<?> findAllStudents(Pageable pageable);

    ResponseData<PageResponseDTO> searchStudents(String maHS, String hoTenHS, String maLop, String diaChi, Pageable pageable);

    ResponseData<?> getStudentDetail(String maHS);

    ResponseData<?> createStudent(StudentRequestDTO studentRequestDTO);
}
