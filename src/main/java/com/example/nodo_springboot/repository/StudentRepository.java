package com.example.nodo_springboot.repository;

import com.example.nodo_springboot.entities.HocSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<HocSinh, String> {

}
