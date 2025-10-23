package com.example.nodo_springboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GiaoVien {
    @Id
    @Column(name = "MaGV", length = 10, columnDefinition = "CHAR(10)")
    String maGV;

    @Column(name = "HoTenGV", nullable = false, length = 100)
    String hoTenGV;

    // Relationships

    @OneToMany(mappedBy = "giaoVienCN")
    @JsonIgnore
    List<Lop> lopChuNhiem;

    @OneToMany(mappedBy = "giaoVienPhuTrach", cascade = CascadeType.ALL)
    @JsonIgnore
    List<PhuTrachBoMon> phuTrachBoMonList;
}
