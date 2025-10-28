package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.PageResponseDTO;
import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentRequestDTO;
import com.example.nodo_springboot.dto.StudentWithScoreReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<?> findAllStudents(Pageable pageable);

    ResponseData<PageResponseDTO> searchStudents(String maHS, String hoTenHS, String maLop, String diaChi, Pageable pageable);

    ResponseData<?> getStudentDetail(String maHS);

    ResponseData<?> addStudent(StudentRequestDTO studentRequestDTO);

    ResponseData<?> updateStudent(String maHS, StudentRequestDTO studentRequestDTO);

    ResponseData<?> deleteStudent(String maHS);
//    ResponseData<?> createStudent(StudentRequestDTO studentRequestDTO);

    void createStudentWithTransaction(String caseNumber, StudentWithScoreReqDTO dto);

}
