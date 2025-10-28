package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import java.util.List;

public interface LopService {
    ResponseData<List<StudentResponseDTO>> getStudentsByLop(String maLop);
}

