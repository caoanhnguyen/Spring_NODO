package com.example.nodo_springboot.mapper;

import com.example.nodo_springboot.dto.StudentRequestDTO;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.Address;
import com.example.nodo_springboot.entities.Student;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, uses = {AddressMapper.class})
public interface StudentMapper {

    @Mapping(target = "status", defaultValue = "INACTIVE")
    Student toStudentEntity(StudentRequestDTO dto);

    @AfterMapping
    default void linkAddresses(@MappingTarget Student student) {
        if (student.getAddresses() != null) {
            for (Address address : student.getAddresses()) {
                address.setStudent(student);
            }
        }
    }

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", constant = "INACTIVE")
    StudentResponseDTO toStudentResponseDTO(Student entity);

    List<StudentResponseDTO> toStudentResponseDTOList(List<Student> entities);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentRequestDTO dto, @MappingTarget Student entity);
}
