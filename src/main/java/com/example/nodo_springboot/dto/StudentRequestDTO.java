package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class StudentRequestDTO {
    @JsonProperty("ma_hs")
    private String maHS;
    @JsonProperty("ho_ten_hs")
    private String hoTenHS;
    @JsonProperty("ho_ten_phu_huynh")
    private String hoTenPH;
    @JsonProperty("ngay_sinh")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String ngaySinh;
    @JsonProperty("dia_chi")
    private String diaChi;
    @JsonProperty("gioi_tinh")
    private String gioiTinh;
    @JsonProperty("ma_lop")
    private String maLop;
}
