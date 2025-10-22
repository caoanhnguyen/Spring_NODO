package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "GIAOVIEN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiaoVien {
    @Id
    @Column(name = "MaGV", length = 10, columnDefinition = "CHAR(10)")
    String maGV;

    @Column(name = "HoTenGV", nullable = false, length = 100)
    String hoTenGV;

    // Relationships

    @OneToMany(mappedBy = "giaoVienCN")
    List<Lop> lopChuNhiem;

    @OneToMany(mappedBy = "giaoVienPhuTrach")
    List<PhuTrachBoMon> phuTrachBoMonList;
}
