package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.List;

@Setter
public class StudentDetailDTO {
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
    String createAt;
    @JsonProperty("updated_at")
    String updateAt;
    @JsonProperty("lop")
    LopResponseDTO lopResponseDTO;
    @JsonProperty("diem")
    List<String> diem;
}
