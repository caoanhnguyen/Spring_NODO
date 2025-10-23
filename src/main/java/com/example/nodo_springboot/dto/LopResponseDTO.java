package com.example.nodo_springboot.dto;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LopResponseDTO {
    String maLop;
    String tenLop;
}
