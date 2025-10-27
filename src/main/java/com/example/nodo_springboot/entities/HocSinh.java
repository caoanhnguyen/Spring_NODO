package com.example.nodo_springboot.entities;

import com.example.nodo_springboot.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "HOCSINH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HocSinh extends BaseEntity {
    @Id
    @Column(name = "MaHS", length = 10)
    String maHS;

    @Column(name = "HoTenHS", nullable = false, length = 100)
    String hoTenHS;

    @Column(name = "HoTenPH", length = 100)
    String hoTenPH;

    @Column(name = "GioiTinh", length = 3)
    Gender gioiTinh;

    @Column(name = "NgaySinh")
    LocalDate ngaySinh;

    @Column(name = "DiaChi")
    String diaChi;

    // Relationships

    // Student -> Lop: many students belong to one class. Use LAZY to avoid loading Lop unless needed.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLop", referencedColumnName = "MaLop")
    Lop lop;

    // Student -> KetQuaHocTap: one-to-many. We often want to cascade PERSIST/REMOVE when the student's lifecycle
    // controls their grades. However, if grades are managed independently, avoid cascading REMOVE. Here we allow
    // CascadeType.ALL as a reasonable default for demo apps; adjust for production per business rules.
    @OneToMany(mappedBy = "hocSinh", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<KetQuaHocTap> ketQuaHocTapList;
}
