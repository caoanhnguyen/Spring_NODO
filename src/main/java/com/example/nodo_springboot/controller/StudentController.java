package com.example.nodo_springboot.controller;

import com.example.nodo_springboot.dto.PageResponse;
import com.example.nodo_springboot.dto.StudentRequestDTO;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.dto.StudentSearchDTO;
import com.example.nodo_springboot.projection.StudentNameEmailProjection;
import com.example.nodo_springboot.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demo/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRequestDTO req) {
        if (studentService.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.create(req));
    }

    @GetMapping
    public Page<StudentResponseDTO> list(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @RequestBody StudentRequestDTO req) {
        try {
            StudentResponseDTO updated = studentService.update(id, req);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/search")
    public PageResponse<StudentResponseDTO> search(@RequestBody StudentSearchDTO dto,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.searchDynamic(dto, pageable);
    }

    @PostMapping("/search-jpql")
    public PageResponse<StudentResponseDTO> searchByJPQL(@RequestBody StudentSearchDTO dto,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.searchByJPQL(dto, pageable);
    }

    @PostMapping("/search-native")
    public Page<StudentResponseDTO> searchByNative(@RequestBody StudentSearchDTO dto,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.searchByNative(dto, pageable);
    }

    // Projection endpoints
    @GetMapping("/projection")
    public Page<StudentNameEmailProjection> projectionList(@RequestParam(required = false) String name,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.findByFullNameProjection(name, pageable);
    }

    @GetMapping("/projection-as-dto")
    public Page<StudentResponseDTO> projectionAsDto(@RequestParam(required = false) String name,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.findByFullNameProjectionAsDto(name, pageable);
    }

//    @PostMapping("/search-dynamic-jpql")
//    public List<Map<String, Object>> searchDynamicJPQL(@RequestBody StudentDynamicSearchDTO dto) {
//        return studentService.searchDynamicFieldsJPQL(dto);
//    }
//
//    @PostMapping("/search-dynamic-native")
//    public List<Map<String, Object>> searchDynamicNative(@RequestBody StudentDynamicSearchDTO dto) {
//        return studentService.searchDynamicFieldsNative(dto);
//    }
}