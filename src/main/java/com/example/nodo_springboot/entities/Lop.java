package com.example.nodo_springboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    // Many classrooms can have one homeroom teacher. We keep LAZY to avoid loading teachers every time a class is fetched.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaGVCN", referencedColumnName = "MaGV")
    GiaoVien giaoVienCN;

    // Một lớp có nhiều học sinh. Sử dụng LAZY (mặc định cho collections) để tránh vấn đề N+1; không cascade REMOVE ở đây vì
    // việc xóa một lớp không nên xóa học sinh trong nhiều bối cảnh nghiệp vụ. Cascade PERSIST/MERGE thường không cần thiết
    // từ phía cha; để trống để an toàn (ứng dụng nên quản lý vòng đời học sinh một cách rõ ràng).
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    @JsonIgnore
    List<HocSinh> hocSinhList;

    // Helper method to add a student to the class while maintaining bidirectional relationship
    public void addHocSinh(HocSinh hocSinh) {
        hocSinh.setLop(this);
        this.getHocSinhList().add(hocSinh);
    }

    // PhuTrachBoMon uses composite PK and is owned by teacher/subject relationships. Keep LAZY and no cascade here.
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    @JsonIgnore
    List<PhuTrachBoMon> phuTrachBoMonList;
}
