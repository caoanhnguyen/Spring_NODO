package com.example.nodo_springboot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDynamicSearchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    // Thêm các trường khác nếu cần
    private List<String> selectFields; // Danh sách các cột cần select
}

