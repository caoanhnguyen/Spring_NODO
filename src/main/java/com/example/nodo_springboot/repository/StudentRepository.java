package com.example.nodo_springboot.repository;

import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.HocSinh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<HocSinh, String> {


    List<HocSinh> findAll();
}
