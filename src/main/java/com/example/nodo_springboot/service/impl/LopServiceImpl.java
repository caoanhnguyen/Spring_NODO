package com.example.nodo_springboot.service.impl;

import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.Lop;
import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.mapper.StudentMapper;
import com.example.nodo_springboot.repository.LopRepository;
import com.example.nodo_springboot.service.LopService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LopServiceImpl implements LopService {
    private final LopRepository lopRepository;
    private final StudentMapper studentMapper;

    public LopServiceImpl(LopRepository lopRepository, StudentMapper studentMapper) {
        this.lopRepository = lopRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public ResponseData<List<StudentResponseDTO>> getStudentsByLop(String maLop) {
        Optional<Lop> lopOpt = lopRepository.findById(maLop);
        if (lopOpt.isEmpty()) {
            return ResponseData.<List<StudentResponseDTO>>builder()
                    .data(null)
                    .message("Lop not found with maLop: " + maLop)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }
        List<HocSinh> students = lopOpt.get().getHocSinhList();
        List<StudentResponseDTO> dtos = students.stream().map(studentMapper::toDto).collect(Collectors.toList());
        return ResponseData.<List<StudentResponseDTO>>builder()
                .data(dtos)
                .message("Get students by Lop successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
}

