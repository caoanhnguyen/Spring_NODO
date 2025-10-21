package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.AddressDTO;
import com.example.nodo_springboot.dto.AddressRequestDTO;
import com.example.nodo_springboot.dto.AddressResponseDTO;
import com.example.nodo_springboot.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AddressMapper {
    Address toAddressEntity(AddressRequestDTO dto);
    AddressResponseDTO toAddressDto(Address entity);
}
