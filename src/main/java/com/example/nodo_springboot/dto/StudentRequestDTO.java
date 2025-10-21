package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String status;
    private String username;
    private String password;
    private List<AddressRequestDTO> addresses;
}
