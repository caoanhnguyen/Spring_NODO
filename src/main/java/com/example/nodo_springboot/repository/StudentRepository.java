package com.example.nodo_springboot.repository;


import com.example.nodo_springboot.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    // Tìm kiếm theo tên hoặc email, có phân trang
    Page<Student> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName, String email, Pageable pageable);
}
