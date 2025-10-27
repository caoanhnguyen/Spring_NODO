package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.LopResponseDTO;
import com.example.nodo_springboot.entities.Lop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LopMapper {


    Lop toEntity(LopResponseDTO lopResponseDTO);

    Lop getLopFromMaLop(String maLop);

    LopResponseDTO toLopResponseDTO(Lop lop);
}
