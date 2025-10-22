package com.example.nodo_springboot.service.Impl;

import com.example.nodo_springboot.dto.*;
import com.example.nodo_springboot.entities.Student;
import com.example.nodo_springboot.mapper.StudentMapper;
import com.example.nodo_springboot.projection.StudentNameEmailProjection;
import com.example.nodo_springboot.repository.StudentRepository;
import com.example.nodo_springboot.service.StudentService;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentMapper mapper;
    private final StudentRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StudentResponseDTO create(StudentRequestDTO dto) {
        Student student = mapper.toStudentEntity(dto);
        Student saved = repo.save(student);
        return mapper.toStudentResponseDTO(saved);
    }

    @Override
//    @Transactional
    public Page<StudentResponseDTO> findAll(Pageable pageable) {
        Page<Student> page = repo.findAll(pageable);
        List<StudentResponseDTO> dtos = mapper.toStudentResponseDTOList(page.getContent());
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Override
//    @Transactional
    public Optional<StudentResponseDTO> findById(Long id) {
        return repo.findById(id).map(mapper::toStudentResponseDTO);
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student student = repo.findById(id).orElseThrow();
        mapper.updateStudentFromDto(dto, student);
        Student updated = repo.save(student);
        return mapper.toStudentResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }


    // JPQL Dynamic Search

    @Override
    public PageResponse<StudentResponseDTO> searchDynamic(StudentSearchDTO dto, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT s FROM Student s WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            jpql.append(" AND s.fullName LIKE :fullName");
            params.put("fullName", "%" + dto.getFullName() + "%");
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            jpql.append(" AND s.email LIKE :email");
            params.put("email", "%" + dto.getEmail() + "%");
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
            jpql.append(" AND s.phoneNumber LIKE :phoneNumber");
            params.put("phoneNumber", "%" + dto.getPhoneNumber() + "%");
        }
        if (dto.getCreatedAtFrom() != null) {
            jpql.append(" AND s.createdAt >= :createdAtFrom");
            params.put("createdAtFrom", dto.getCreatedAtFrom());
        }
        if (dto.getCreatedAtTo() != null) {
            jpql.append(" AND s.createdAt <= :createdAtTo");
            params.put("createdAtTo", dto.getCreatedAtTo());
        }
        if (dto.getLastModifiedAtFrom() != null) {
            jpql.append(" AND s.lastModifiedAt >= :lastModifiedAtFrom");
            params.put("lastModifiedAtFrom", dto.getLastModifiedAtFrom());
        }
        if (dto.getLastModifiedAtTo() != null) {
            jpql.append(" AND s.lastModifiedAt <= :lastModifiedAtTo");
            params.put("lastModifiedAtTo", dto.getLastModifiedAtTo());
        }
        TypedQuery<Student> query = entityManager.createQuery(jpql.toString(), Student.class);
        params.forEach(query::setParameter);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Student> students = query.getResultList();
        long total = countDynamic(dto);
        List<StudentResponseDTO> dtos = mapper.toStudentResponseDTOList(students);
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
        return new PageResponse<>(dtos, pageable.getPageNumber(), pageable.getPageSize(), total, totalPages);
    }

    // Criteria API Dynamic Search
//    @Override
//    public PageResponse<StudentResponseDTO> searchDynamic(StudentSearchDTO dto, Pageable pageable) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
//        Root<Student> root = cq.from(Student.class);
//        List<Predicate> predicates = new java.util.ArrayList<>();
//        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
//            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + dto.getFullName().toLowerCase() + "%"));
//        }
//        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
//            predicates.add(cb.like(cb.lower(root.get("email")), "%" + dto.getEmail().toLowerCase() + "%"));
//        }
//        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
//            predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + dto.getPhoneNumber().toLowerCase() + "%"));
//        }
//        cq.where(predicates.toArray(new Predicate[0]));
//        cq.orderBy(cb.asc(root.get("id")));
//        TypedQuery<Student> query = entityManager.createQuery(cq);
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//        List<Student> students = query.getResultList();
//        // Count query
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        Root<Student> countRoot = countQuery.from(Student.class);
//        countQuery.select(cb.count(countRoot));
//        List<Predicate> countPredicates = new java.util.ArrayList<>();
//        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
//            countPredicates.add(cb.like(cb.lower(countRoot.get("fullName")), "%" + dto.getFullName().toLowerCase() + "%"));
//        }
//        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
//            countPredicates.add(cb.like(cb.lower(countRoot.get("email")), "%" + dto.getEmail().toLowerCase() + "%"));
//        }
//        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
//            countPredicates.add(cb.like(cb.lower(countRoot.get("phoneNumber")), "%" + dto.getPhoneNumber().toLowerCase() + "%"));
//        }
//        countQuery.where(countPredicates.toArray(new Predicate[0]));
//        long total = entityManager.createQuery(countQuery).getSingleResult();
//        List<StudentResponseDTO> dtos = mapper.toStudentResponseDTOList(students);
//        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
//        return new PageResponse<>(dtos, pageable.getPageNumber(), pageable.getPageSize(), total, totalPages);
//    }


    private long countDynamic(StudentSearchDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Student> root = cq.from(Student.class);
        cq.select(cb.count(root));
        List<Predicate> predicates = new java.util.ArrayList<>();
        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + dto.getFullName().toLowerCase() + "%"));
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + dto.getEmail().toLowerCase() + "%"));
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + dto.getPhoneNumber().toLowerCase() + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public PageResponse<StudentResponseDTO> searchByJPQL(StudentSearchDTO dto, Pageable pageable) {
        Page<Student> page = repo.searchByJPQL(
                dto.getFullName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                pageable
        );
        List<StudentResponseDTO> dtos = mapper.toStudentResponseDTOList(page.getContent());
        return new PageResponse<>(dtos, pageable.getPageNumber(), pageable.getPageSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Page<StudentResponseDTO> searchByNative(StudentSearchDTO dto, Pageable pageable) {
        Page<Student> page = repo.searchByNative(
                dto.getFullName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                pageable
        );
        List<StudentResponseDTO> dtos = mapper.toStudentResponseDTOList(page.getContent());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Override
    public Page<StudentNameEmailProjection> findByFullNameProjection(String name, Pageable pageable) {
        return repo.findByFullNameContaining(name == null ? "" : name, pageable);
    }

    @Override
    public Page<StudentResponseDTO> findByFullNameProjectionAsDto(String name, Pageable pageable) {
        Page<StudentNameEmailProjection> page = repo.findByFullNameContaining(name == null ? "" : name, pageable);
        return page.map(p -> StudentResponseDTO.builder()
                .fullName(p.getFullName())
                .email(p.getEmail())
                .build());
    }
//
//    public List<Map<String, Object>> searchDynamicFieldsJPQL(StudentDynamicSearchDTO dto) {
//        StringBuilder jpql = new StringBuilder("SELECT ");
//        if (dto.getSelectFields() == null || dto.getSelectFields().isEmpty()) {
//            throw new IllegalArgumentException("selectFields must not be empty");
//        }
//        // Build select clause
//        for (int i = 0; i < dto.getSelectFields().size(); i++) {
//            jpql.append("s.").append(dto.getSelectFields().get(i));
//            if (i < dto.getSelectFields().size() - 1) jpql.append(", ");
//        }
//        jpql.append(" FROM Student s WHERE 1=1");
//        Map<String, Object> params = new HashMap<>();
//        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
//            jpql.append(" AND s.fullName LIKE :fullName");
//            params.put("fullName", "%" + dto.getFullName() + "%");
//        }
//        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
//            jpql.append(" AND s.email LIKE :email");
//            params.put("email", "%" + dto.getEmail() + "%");
//        }
//        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
//            jpql.append(" AND s.phoneNumber LIKE :phoneNumber");
//            params.put("phoneNumber", "%" + dto.getPhoneNumber() + "%");
//        }
//        log.info("JPQL Query: {}", jpql);
//        TypedQuery<Object[]> query = entityManager.createQuery(jpql.toString(), Object[].class);
//        params.forEach(query::setParameter);
//        List<Object[]> results = query.getResultList();
//        // Convert result to List<Map<String, Object>>
//        List<Map<String, Object>> mappedResults = new java.util.ArrayList<>();
//        for (Object[] row : results) {
//            Map<String, Object> map = new HashMap<>();
//            for (int i = 0; i < dto.getSelectFields().size(); i++) {
//                map.put(dto.getSelectFields().get(i), row[i]);
//            }
//            mappedResults.add(map);
//        }
//        return mappedResults;
//    }
//
//    public List<Map<String, Object>> searchDynamicFieldsNative(StudentDynamicSearchDTO dto) {
//        StringBuilder sql = new StringBuilder("SELECT ");
//        if (dto.getSelectFields() == null || dto.getSelectFields().isEmpty()) {
//            throw new IllegalArgumentException("selectFields must not be empty");
//        }
//        for (int i = 0; i < dto.getSelectFields().size(); i++) {
//            // Chuyển sang tên cột trong DB nếu cần (ví dụ: fullName -> full_name)
//            String col = dto.getSelectFields().get(i);
//            sql.append(col);
//            if (i < dto.getSelectFields().size() - 1) sql.append(", ");
//        }
//        sql.append(" FROM students WHERE 1=1");
//        List<Object> params = new java.util.ArrayList<>();
//        if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
//            sql.append(" AND full_name LIKE ?");
//            params.add("%" + dto.getFullName() + "%");
//        }
//        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
//            sql.append(" AND email LIKE ?");
//            params.add("%" + dto.getEmail() + "%");
//        }
//        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
//            sql.append(" AND phone_number LIKE ?");
//            params.add("%" + dto.getPhoneNumber() + "%");
//        }
//        log.info("Native SQL Query: {}", sql);
//        Query query = entityManager.createNativeQuery(sql.toString());
//        for (int i = 0; i < params.size(); i++) {
//            query.setParameter(i + 1, params.get(i));
//        }
//        List<Object[]> results = query.getResultList();
//        List<Map<String, Object>> mappedResults = new java.util.ArrayList<>();
//        for (Object[] row : results) {
//            Map<String, Object> map = new HashMap<>();
//            for (int i = 0; i < dto.getSelectFields().size(); i++) {
//                map.put(dto.getSelectFields().get(i), row[i]);
//            }
//            mappedResults.add(map);
//        }
//        return mappedResults;
//    }
}
