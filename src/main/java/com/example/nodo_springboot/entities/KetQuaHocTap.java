package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@Table(name = "KETQUAHOCTAP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KetQuaHocTap {
    @EmbeddedId
    KetQuaHocTapId id;

    @Column(name = "DiemThiGiuaKy")
    Double diemThiGiuaKy;

    @Column(name = "DiemThiCuoiKy")
    Double diemThiCuoiKy;

    @Column(name = "NgayGioThiCuoiKy")
    LocalDateTime ngayGioThiCuoiKy;

    // Relationships

    @ManyToOne
    @MapsId("maHS")
    @JoinColumn(name = "MaHS")
    HocSinh hocSinh;

    @ManyToOne
    @MapsId("maMH")
    @JoinColumn(name = "MaMH")
    MonHoc monHoc;
}

