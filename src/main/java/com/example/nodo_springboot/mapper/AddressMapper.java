package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.AddressDTO;
import com.example.nodo_springboot.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AddressMapper {
    Address toAddressEntity(AddressDTO dto);
    AddressDTO toAddressDto(Address entity);
}
