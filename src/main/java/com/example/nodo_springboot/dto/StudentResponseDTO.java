package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class StudentResponseDTO {
    @JsonProperty("ma_hs")
    String maHS;
    @JsonProperty("ho_ten_hs")
    String hoTenHS;
    @JsonProperty("ho_ten_ph")
    String hoTenPH;
    @JsonProperty("gioi_tinh")
    String gioiTinh;
    @JsonProperty("ngay_sinh")
    String ngaySinh;
    @JsonProperty("dia_chi")
    String diaChi;
    @JsonProperty("created_at")
    String createdAt;
    @JsonProperty("updated_at")
    String updatedAt;
    @JsonProperty("ma_lop")
    String maLop;
    @JsonProperty("gvcn")
    String GVCN;


}
