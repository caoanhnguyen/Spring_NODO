package com.example.nodo_springboot.controller;

import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("")
    public Page<HocSinh> findByMaLop(@RequestParam(defaultValue = "0", required = false) int page,
                                     @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return studentRepository.findAll(pageable);
    }
}
