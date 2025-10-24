package com.example.nodo_springboot.service.impl;

import com.example.nodo_springboot.dto.PageResponseDTO;
import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.mapper.StudentMapper;
import com.example.nodo_springboot.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final com.example.nodo_springboot.repository.StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @PersistenceContext
    private EntityManager em;

    public StudentServiceImpl(com.example.nodo_springboot.repository.StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public Page<?> findAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ResponseData<PageResponseDTO> searchStudents(String maHS, String hoTenHS, String maLop, String diaChi, Pageable pageable) {
        boolean joinLop = maLop != null && !maLop.isBlank();

        String baseFrom = " from HocSinh hs";
        String joinLopFragment = " left join hs.lop l";

        List<String> whereClauses = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (maHS != null && !maHS.isBlank()) {
            whereClauses.add("lower(hs.maHS) like :maHS");
            params.put("maHS", "%" + maHS.toLowerCase() + "%");
        }
        if (hoTenHS != null && !hoTenHS.isBlank()) {
            whereClauses.add("lower(hs.hoTenHS) like :hoTenHS");
            params.put("hoTenHS", "%" + hoTenHS.toLowerCase() + "%");
        }
        if (maLop != null && !maLop.isBlank()) {
            whereClauses.add("lower(l.maLop) = :maLop");
            params.put("maLop", maLop.toLowerCase());
        }
        if (diaChi != null && !diaChi.isBlank()) {
            whereClauses.add("lower(hs.diaChi) like :diaChi");
            params.put("diaChi", "%" + diaChi.toLowerCase() + "%");
        }

        // Count query
        StringBuilder countJpql = new StringBuilder("select count(hs)");
        countJpql.append(baseFrom);
        if (joinLop) countJpql.append(joinLopFragment);
        if (!whereClauses.isEmpty()) {
            countJpql.append(" where ").append(String.join(" and ", whereClauses));
        }

        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
        if (!params.isEmpty()) params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        if (total == 0L) {
            return ResponseData.<PageResponseDTO>builder()
                    .data(null)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        if (!joinLop) {
            StringBuilder selectJpql = new StringBuilder("select hs");
            selectJpql.append(baseFrom);
            if (!whereClauses.isEmpty()) selectJpql.append(" where ").append(String.join(" and ", whereClauses));
            selectJpql.append(" order by hs.maHS asc");

            TypedQuery<HocSinh> q = em.createQuery(selectJpql.toString(), HocSinh.class);
            params.forEach(q::setParameter);
            q.setFirstResult((int) pageable.getOffset());
            q.setMaxResults(pageable.getPageSize());
            List<HocSinh> list = q.getResultList();

            List<StudentResponseDTO> dtos = list.stream().map(studentMapper::toDto).collect(Collectors.toList());
            PageResponseDTO<Object> pageResponeDTO = PageResponseDTO.builder()
                    .items(dtos)
                    .pageNo(pageable.getPageNumber())
                    .pageSize(pageable.getPageSize())
                    .totalPages((int) Math.ceil((double) total / pageable.getPageSize()))
                    .build();

            return ResponseData.<PageResponseDTO>builder()
                    .data(pageResponeDTO)
                    .message("Search students successfully")
                    .status(HttpStatus.OK.value())
                    .build();
        }

        // Nếu có join với lớp, thực hiện truy vấn 2 bước
        // 1) Lấy danh sách IDs
        StringBuilder selectIds = new StringBuilder("select hs.maHS");
        selectIds.append(baseFrom);
        if (joinLop) selectIds.append(joinLopFragment);
        if (!whereClauses.isEmpty()) selectIds.append(" where ").append(String.join(" and ", whereClauses));
        selectIds.append(" order by hs.maHS asc");

        TypedQuery<String> idQuery = em.createQuery(selectIds.toString(), String.class);
        params.forEach(idQuery::setParameter);
        idQuery.setFirstResult((int) pageable.getOffset());
        idQuery.setMaxResults(pageable.getPageSize());
        List<String> ids = idQuery.getResultList();

        if (ids.isEmpty()) {
            return ResponseData.<PageResponseDTO>builder()
                    .data(PageResponseDTO.<StudentResponseDTO>builder()
                            .items(null)
                            .pageNo(pageable.getPageNumber())
                            .pageSize(pageable.getPageSize())
                            .totalPages(0)
                            .build())
                    .message("No students found")
                    .status(HttpStatus.OK.value())
                    .build();
        }

        // 2) Fetch đầy đủ thực thể với join fetch
        StringBuilder fetchJpql = new StringBuilder("select distinct hs from HocSinh hs left join fetch hs.lop l where hs.maHS in :ids");
        TypedQuery<HocSinh> fetchQuery = em.createQuery(fetchJpql.toString(), HocSinh.class);
        fetchQuery.setParameter("ids", ids);
        List<HocSinh> fetched = fetchQuery.getResultList();

        List<StudentResponseDTO> dtos = fetched.stream().map(studentMapper::toDto).collect(Collectors.toList());

        PageResponseDTO<Object> pageResponeDTO = PageResponseDTO.builder()
                .items(dtos)
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages((int) Math.ceil((double) total / pageable.getPageSize()))
                .build();

        return ResponseData.<PageResponseDTO>builder()
                .data(pageResponeDTO)
                .message("Search students successfully")
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    @Transactional
    public ResponseData<?> getStudentDetail(String maHS) {
        Optional<HocSinh> optionalHocSinh = studentRepository.findById(maHS);
        if (optionalHocSinh.isEmpty()) {
            return ResponseData.builder()
                    .data(null)
                    .message("Student not found with maHS: " + maHS)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }
        HocSinh hocSinh = optionalHocSinh.get();
        var studentDetailDTO = studentMapper.toDetailDto(hocSinh);
        return ResponseData.builder()
                .data(studentDetailDTO)
                .message("Get student detail successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
}
