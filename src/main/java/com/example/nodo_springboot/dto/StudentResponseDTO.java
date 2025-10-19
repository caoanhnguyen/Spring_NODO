package com.example.nodo_springboot.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
}
