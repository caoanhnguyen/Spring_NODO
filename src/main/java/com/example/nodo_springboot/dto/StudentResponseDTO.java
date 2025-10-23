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
    String createAt;
    @JsonProperty("updated_at")
    String updateAt;
    @JsonProperty("ma_lop")
    String maLop;
    @JsonProperty("gvcn")
    String GVCN;

    public StudentResponseDTO(String maHS, String hoTenHS, String hoTenPH,String gioiTinh, String ngaySinh, String diaChi, String createAt, String updateAt, String maLop, String GVCN) {
        this.maHS = maHS;
        this.hoTenHS = hoTenHS;
        this.hoTenPH = hoTenPH;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.maLop = maLop;
        this.GVCN = GVCN;
    }

}
