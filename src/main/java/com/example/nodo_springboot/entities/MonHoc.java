package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "MONHOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonHoc {
    @Id
    @Column(name = "MaMH", length = 10, columnDefinition = "CHAR(10)")
    String maMH;

    @Column(name = "TenMH", nullable = false, length = 100)
    String tenMH;

    // Relationships

    // Subject -> KetQuaHocTap: grades belong to student+subject; do NOT cascade from subject side by default.
    @OneToMany(mappedBy = "monHoc", fetch = FetchType.LAZY)
    List<KetQuaHocTap> ketQuaHocTapList;

    // Subject -> PhuTrachBoMon: association entity. No cascade here.
    @OneToMany(mappedBy = "monHoc", fetch = FetchType.LAZY)
    List<PhuTrachBoMon> phuTrachBoMonList;
}
