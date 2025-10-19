package com.example.nodo_springboot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private LocalDateTime lastModifiedAtFrom;
    private LocalDateTime lastModifiedAtTo;
    // Có thể bổ sung thêm các trường khác nếu cần
}
