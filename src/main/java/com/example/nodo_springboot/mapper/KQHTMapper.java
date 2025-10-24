package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.entities.KetQuaHocTap;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;

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


//    List<String> toDiemList(HocSinh hocSinh);

}
