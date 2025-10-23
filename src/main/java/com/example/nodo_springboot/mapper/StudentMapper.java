package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.HocSinh;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
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

    @Mapping(target = "maLop", source = "lop.maLop")
    @Mapping(target = "GVCN", source = "lop.giaoVienCN.hoTenGV")
    @Mapping(target = "createAt", source = "createdAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "updateAt", source = "updatedAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "ngaySinh", source = "ngaySinh", qualifiedByName = "localDateToString")
    StudentResponseDTO toDto(HocSinh hocSinh);
}

