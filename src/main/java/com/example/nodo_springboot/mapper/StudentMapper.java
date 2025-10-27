package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.StudentDetailDTO;
import com.example.nodo_springboot.dto.StudentRequestDTO;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.HocSinh;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {LopMapper.class, KQHTMapper.class})
public interface StudentMapper {

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    @Named("localDateToString")
    default String dateTimeToString(LocalDate dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    default LocalDateTime stringToLocalDateTime(String str) {
        return str != null ? LocalDateTime.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String str) {
        return str != null ? LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }


    // Mapping điểm học tập từ entity HocSinh sang trng List<String> diem trong StudentDetailDTO, định dạng điểm là "Môn: Điểm"
    default List<String> mapDiemHocTap(HocSinh hocSinh) {

        if (hocSinh.getKetQuaHocTapList() == null) {
            return null;
        }
        return hocSinh.getKetQuaHocTapList().stream()
                .map(ketQua -> ketQua.getId().getHocKy() + ": "
                        + ketQua.getMonHoc().getTenMH() + ": "
                        + String.format("%.2f", ketQua.getDiemThiCuoiKy()*0.7 + ketQua.getDiemThiGiuaKy()*0.3))
                .toList();

    }

    @Mapping(target = "maLop", source = "lop.maLop")
    @Mapping(target = "GVCN", source = "lop.giaoVienCN.hoTenGV")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "ngaySinh", source = "ngaySinh", qualifiedByName = "localDateToString")
    StudentResponseDTO toDto(HocSinh hocSinh);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "ngaySinh", source = "ngaySinh", qualifiedByName = "localDateToString")
    @Mapping(target = "lopResponseDTO", source = "lop")
    @Mapping(target = "diem", expression = "java(mapDiemHocTap(hocSinh))")
    StudentDetailDTO toDetailDto(HocSinh hocSinh);

    // =====================================================================================

    @Mapping(target = "ngaySinh", source = "ngaySinh", qualifiedByName = ("stringToLocalDate"))
    HocSinh toEntity(StudentRequestDTO studentRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ngaySinh", source = "ngaySinh", qualifiedByName = ("stringToLocalDate"))
    void updateEntityFromDto(StudentRequestDTO studentRequestDTO, @MappingTarget HocSinh hocSinh);

}

