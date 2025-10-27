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

    // Teacher -> Lop (homeroom): one teacher may have many classes. We do NOT cascade REMOVE because
    // deleting a teacher shouldn't delete classes. Keep LAZY.
    @OneToMany(mappedBy = "giaoVienCN", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Lop> lopChuNhiem;

    // Teacher -> PhuTrachBoMon: this is effectively an association entity owned by teacher; cascading ALL
    // here is reasonable if PhuTrachBoMon has no lifecycle outside the teacher domain.
    @OneToMany(mappedBy = "giaoVienPhuTrach", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<PhuTrachBoMon> phuTrachBoMonList;
}
