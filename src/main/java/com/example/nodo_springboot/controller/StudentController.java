package com.example.nodo_springboot.controller;


import com.example.nodo_springboot.dto.StudentDTO;
import com.example.nodo_springboot.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/demo/students", method =  RequestMethod.POST)
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@ModelAttribute StudentDTO req) {
        if (studentService.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.create(req));
    }

    @GetMapping
    public List<StudentDTO> list() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody StudentDTO req) {
        try {
            StudentDTO updated = studentService.update(id, req);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "") String keyword,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        var pageable = org.springframework.data.domain.PageRequest.of(page, size);
        var result = studentService.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }
}