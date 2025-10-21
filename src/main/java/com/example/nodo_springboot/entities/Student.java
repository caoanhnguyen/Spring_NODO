package com.example.nodo_springboot.entities;

import com.example.nodo_springboot.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
    name = "Student.addresses",
    attributeNodes = @NamedAttributeNode("addresses")
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // H2 và MySQL đều support IDENTITY
    private Long id;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private Status status;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
}