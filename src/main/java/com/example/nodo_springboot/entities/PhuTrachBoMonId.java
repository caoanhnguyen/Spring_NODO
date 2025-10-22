package com.example.nodo_springboot.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuTrachBoMonId implements Serializable {
    @Column(name = "MaGVPT", length = 10, columnDefinition = "CHAR(10)")
    String maGVPT;
    @Column(name = "MaLop", length = 10, columnDefinition = "CHAR(10)")
    String maLop;
    @Column(name = "MaMH", length = 10, columnDefinition = "CHAR(10)")
    String maMH;
    @Column(name = "HocKy", length = 10, columnDefinition = "CHAR(10)")
    String hocKy;
}
