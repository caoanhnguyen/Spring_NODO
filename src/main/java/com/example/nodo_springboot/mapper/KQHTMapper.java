package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.ScoreDTO;
import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.entities.KetQuaHocTap;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface KQHTMapper {

    @Named("mapDiemHocTap")
    default List<String> mapDiemHocTap(List<KetQuaHocTap> ketQuaHocTap) {

        if (ketQuaHocTap == null) {
            return null;
        }
        return ketQuaHocTap.stream()
                .map(ketQua -> ketQua.getId().getHocKy() + ": "
                        + ketQua.getMonHoc().getTenMH() + ": "
                        + String.format("%.2f", ketQua.getDiemThiCuoiKy()*0.7 + ketQua.getDiemThiGiuaKy()*0.3))
                .toList();

    }

    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String str) {
        return str != null ? LocalDateTime.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }


//    List<String> toDiemList(HocSinh hocSinh);
    @Mappings({
            @org.mapstruct.Mapping(source = "maHS", target = "id.maHS"),
            @org.mapstruct.Mapping(source = "dto.maMH", target = "id.maMH"),
            @org.mapstruct.Mapping(source = "dto.hocKy", target = "id.hocKy"),
            @org.mapstruct.Mapping(source = "dto.diemThiGiuaKy", target = "diemThiGiuaKy"),
            @org.mapstruct.Mapping(source = "dto.diemThiCuoiKy", target = "diemThiCuoiKy"),
            @org.mapstruct.Mapping(source = "dto.ngayGioThiCuoiKy", target = "ngayGioThiCuoiKy", qualifiedByName = "stringToLocalDateTime")
    })
    KetQuaHocTap toEntity(ScoreDTO dto, String maHS);

}
