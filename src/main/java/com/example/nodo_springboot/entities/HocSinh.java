package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "HOCSINH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HocSinh {
    @Id
    @Column(name = "MaHS", length = 10)
    String maHS;

    @Column(name = "HoTenHS", nullable = false, length = 100)
    String hoTenHS;

    @Column(name = "HoTenPH", length = 100)
    String hoTenPH;

    @Column(name = "GioiTinh", length = 3)
    String gioiTinh;

    @Column(name = "DiaChi", length = 255)
    String diaChi;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "MaLop", referencedColumnName = "MaLop")
    Lop lop;

    @OneToMany(mappedBy = "hocSinh")
    List<KetQuaHocTap> ketQuaHocTapList;
}

