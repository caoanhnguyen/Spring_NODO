package com.example.nodo_springboot.repository;


import com.example.nodo_springboot.entities.Student;
import com.example.nodo_springboot.projection.StudentNameEmailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Pageable findAll
    Page<Student> findAll(Pageable pageable);


    boolean existsByEmail(String email);
    // Tìm kiếm theo tên hoặc email, có phân trang
    Page<Student> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName, String email, Pageable pageable);

    // JPQL search
   @Query("SELECT s FROM Student s WHERE (:fullName IS NULL OR LOWER(s.fullName) " +
           "LIKE LOWER(CONCAT('%', :fullName, '%'))) AND (:email IS NULL OR LOWER(s.email) " +
           "LIKE LOWER(CONCAT('%', :email, '%'))) AND (:phoneNumber IS NULL OR s.phoneNumber LIKE CONCAT('%', :phoneNumber, '%'))")
    Page<Student> searchByJPQL(@Param("fullName") String fullName,
                               @Param("email") String email,
                               @Param("phoneNumber") String phoneNumber,
                               Pageable pageable);

    // Native SQL search
    @Query(
        value = "SELECT * FROM students s WHERE (:fullName IS NULL OR LOWER(s.full_name) LIKE LOWER(CONCAT('%', :fullName, '%')))" +
                " AND (:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%')))" +
                " AND (:phoneNumber IS NULL OR s.phone_number LIKE CONCAT('%', :phoneNumber, '%'))",
        countQuery = "SELECT count(*) FROM students s WHERE (:fullName IS NULL OR LOWER(s.full_name) LIKE LOWER(CONCAT('%', :fullName, '%')))" +
                     " AND (:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%')))" +
                     " AND (:phoneNumber IS NULL OR s.phone_number LIKE CONCAT('%', :phoneNumber, '%'))",
        nativeQuery = true
    )
    Page<Student> searchByNative(@Param("fullName") String fullName,
                                @Param("email") String email,
                                @Param("phoneNumber") String phoneNumber,
                                Pageable pageable);

    Page<StudentNameEmailProjection> findByFullNameContaining(String name, Pageable pageable);
}
