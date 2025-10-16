package com.example.nodo_springboot.service.Impl;

import com.example.nodo_springboot.dto.StudentDTO;
import com.example.nodo_springboot.entities.Student;
import com.example.nodo_springboot.repository.StudentRepository;
import com.example.nodo_springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repo;

    @Autowired
    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    private StudentDTO toDTO(Student s) {
        return new StudentDTO(s.getId(), s.getFullName(), s.getEmail(), s.getCreatedAt());
    }
    private Student toEntity(StudentDTO dto) {
        Student s = new Student();
        s.setId(dto.getId());
        s.setFullName(dto.getFullName());
        s.setEmail(dto.getEmail());
        s.setCreatedAt(dto.getCreatedAt());
        return s;
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getFullName(), studentDTO.getEmail());
        Student saved = repo.save(student);
        return toDTO(saved);
    }

    @Override
    public List<StudentDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> findById(Long id) {
        return repo.findById(id).map(this::toDTO);
    }

    @Override
    public StudentDTO update(Long id, StudentDTO studentDTO) {
        return repo.findById(id).map(existing -> {
            existing.setFullName(studentDTO.getFullName());
            existing.setEmail(studentDTO.getEmail());
            Student updated = repo.save(existing);
            return toDTO(updated);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<StudentDTO> search(String keyword, Pageable pageable) {
        Page<Student> page = repo.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        List<StudentDTO> dtos = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
