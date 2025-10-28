// java
package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    @JsonProperty("ma_mh")
    private String maMH;
    @JsonProperty("hoc_ky")
    private String hocKy;
    @JsonProperty("diem_thi_giua_ky")
    private Double diemThiGiuaKy;
    @JsonProperty("diem_thi_cuoi_ky")
    private Double diemThiCuoiKy;
    @JsonProperty("ngay_gio_thi_cuoi_ky")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime ngayGioThiCuoiKy;
}
