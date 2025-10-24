package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
public class LopResponseDTO {
    @JsonProperty("ma_lop")
    String maLop;
    @JsonProperty("ten_lop")
    String tenLop;
}
