package com.example.nodo_springboot.controller;

import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.service.LopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lop")
public class LopController {
    private final LopService lopService;

    public LopController(LopService lopService) {
        this.lopService = lopService;
    }

    @GetMapping("/{maLop}/students")
    public ResponseEntity<ResponseData<List<StudentResponseDTO>>> getStudentsByLop(@PathVariable String maLop) {
        ResponseData<List<StudentResponseDTO>> response = lopService.getStudentsByLop(maLop);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

