package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "PHUTRACHBOMON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuTrachBoMon {
    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "maGVPT", column = @Column(name = "MaGVPT", length = 10, nullable = false, columnDefinition = "CHAR(10)")),
        @AttributeOverride(name = "maLop", column = @Column(name = "MaLop", length = 10, nullable = false, columnDefinition = "CHAR(10)")),
        @AttributeOverride(name = "maMH", column = @Column(name = "MaMH", length = 10, nullable = false, columnDefinition = "CHAR(10)")),
        @AttributeOverride(name = "hocKy", column = @Column(name = "HocKy", length = 10, nullable = false, columnDefinition = "CHAR(10)"))
    })
    PhuTrachBoMonId id;

    // Relationships

    // This is a join/association table modeled as entity with composite id. The association fields are the owners
    // of the relationship. We generally do NOT cascade from this side to the referenced entities.
    @ManyToOne
    @MapsId("maGVPT")
    @JoinColumn(name = "MaGVPT", referencedColumnName = "MaGV", nullable = false, columnDefinition = "CHAR(10)")
    GiaoVien giaoVienPhuTrach;

    @ManyToOne
    @MapsId("maLop")
    @JoinColumn(name = "MaLop", referencedColumnName = "MaLop", nullable = false, columnDefinition = "CHAR(10)")
    Lop lop;

    @ManyToOne
    @MapsId("maMH")
    @JoinColumn(name = "MaMH", referencedColumnName = "MaMH", nullable = false, columnDefinition = "CHAR(10)")
    MonHoc monHoc;

}
