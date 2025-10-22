package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "LOP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lop {
    @Id
    @Column(name = "MaLop", length = 10, columnDefinition = "CHAR(10)")
    String maLop;

    @Column(name = "TenLop", nullable = false, length = 50)
    String tenLop;

    @Column(name = "NamHoc", length = 9)
    String namHoc;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "MaGVCN", referencedColumnName = "MaGV")
    GiaoVien giaoVienCN;

    @OneToMany(mappedBy = "lop")
    List<HocSinh> hocSinhList;

    @OneToMany(mappedBy = "lop")
    List<PhuTrachBoMon> phuTrachBoMonList;
}
