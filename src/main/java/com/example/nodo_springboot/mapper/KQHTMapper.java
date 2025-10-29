package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.ScoreDTO;
import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.entities.KetQuaHocTap;
import com.example.nodo_springboot.entities.KetQuaHocTapId;
import com.example.nodo_springboot.entities.MonHoc;
import org.mapstruct.*;
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
    @Mapping(source = "dto.ngayGioThiCuoiKy", target = "ngayGioThiCuoiKy", qualifiedByName = "stringToLocalDateTime")
    @Mapping(target = "hocSinh", ignore = true)
    @Mapping(target = "monHoc", ignore = true)
    @Mapping(target = "id", ignore = true)
    KetQuaHocTap toEntity(ScoreDTO dto, @Context HocSinh hocSinh, @Context MonHoc monHoc);

    @AfterMapping
    default void setReferences(@MappingTarget KetQuaHocTap entity, ScoreDTO dto, @Context HocSinh hocSinh, @Context MonHoc monHoc) {
        entity.setId(new KetQuaHocTapId(hocSinh.getMaHS(), monHoc.getMaMH(), dto.getHocKy()));
        entity.setHocSinh(hocSinh);
        entity.setMonHoc(monHoc);
    }

}
