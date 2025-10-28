package com.example.nodo_springboot.service;

import com.example.nodo_springboot.dto.StudentWithScoreReqDTO;

public interface StudentProcessService {
    void processStudentCreation(String caseNumber, StudentWithScoreReqDTO dto);
}
