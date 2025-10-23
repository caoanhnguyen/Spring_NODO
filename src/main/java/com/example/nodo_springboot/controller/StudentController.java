package com.example.nodo_springboot.controller;

import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public ResponseData<?> searchStudents(
            @RequestParam(required = false) String maHS,
            @RequestParam(required = false) String hoTenHS,
            @RequestParam(required = false) String maLop,
            @RequestParam(required = false) String diaChi,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return studentService.searchStudents(maHS, hoTenHS, maLop, diaChi, pageable);
    }
}
