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

    // This is the owner side of the relation; we do not cascade from grade to student/subject.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maHS")
    @JoinColumn(name = "MaHS")
    HocSinh hocSinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maMH")
    @JoinColumn(name = "MaMH")
    MonHoc monHoc;
}
