package com.example.nodo_springboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "LOP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonIgnore
    List<HocSinh> hocSinhList;

    @OneToMany(mappedBy = "lop")
    @JsonIgnore
    List<PhuTrachBoMon> phuTrachBoMonList;
}
