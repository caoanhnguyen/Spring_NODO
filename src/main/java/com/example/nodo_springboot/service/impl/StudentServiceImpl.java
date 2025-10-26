package com.example.nodo_springboot.service.impl;

import com.example.nodo_springboot.dto.PageResponseDTO;
import com.example.nodo_springboot.dto.ResponseData;
import com.example.nodo_springboot.dto.StudentRequestDTO;
import com.example.nodo_springboot.dto.StudentResponseDTO;
import com.example.nodo_springboot.entities.HocSinh;
import com.example.nodo_springboot.mapper.StudentMapper;
import com.example.nodo_springboot.service.StudentService;
import jakarta.persistence.*;
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

    @Override
    @Transactional
    public ResponseData<?> createStudent(StudentRequestDTO dto) {

        // LƯU flush mode hiện tại và đặt về COMMIT để tránh auto-flush trước query
        FlushModeType prev = em.getFlushMode();
        em.setFlushMode(FlushModeType.COMMIT);
        try {
            List<String> timeline = new ArrayList<>();

            HocSinh hsTransient = studentMapper.toEntity(dto); // TRANSIENT
            timeline.add("1) TRANSIENT: em.contains(hsTransient) = " + em.contains(hsTransient));

            em.persist(hsTransient); // -> MANAGED (chưa auto-flush vì COMMIT)
            timeline.add("2) MANAGED (after persist): em.contains(hsTransient) = " + em.contains(hsTransient));

            // Thay đổi khi đang MANAGED, vẫn chưa flush
            hsTransient.setHoTenHS("Name after persist (managed)");

            // Gọi find ở đây sẽ KHÔNG gây INSERT trước query (vì flush mode = COMMIT)
            HocSinh createdHocSinh = em.find(HocSinh.class, hsTransient.getMaHS());
            timeline.add("2.x) FIND returns from PC, no auto-flush");

            // Chủ động flush khi bạn muốn (chỉ 1 INSERT với giá trị cuối cùng)
            em.flush();
            timeline.add("2.1) FLUSH: đồng bộ INSERT ra DB");

            System.out.println("Name after flush: " + createdHocSinh.getHoTenHS());
            System.out.println("createdHS status after flush: " + em.contains(createdHocSinh));

            // DETACH
            em.detach(createdHocSinh);
            timeline.add("3) DETACHED: em.contains(createdHocSinh) = " + em.contains(createdHocSinh));

            createdHocSinh.setHoTenHS("Changed while DETACHED (no SQL)");

            HocSinh hsManaged = em.merge(createdHocSinh); // Managed copy
            System.out.println("UPDATE");
            timeline.add("4) MERGE → MANAGED copy: em.contains(hsManaged) = " + em.contains(hsManaged));
            System.out.println("createdHS status: "+ em.contains(createdHocSinh));
            System.out.println("Name after merge: " + hsManaged.getHoTenHS());

            hsManaged.setHoTenHS("Name after merge (managed)");

            System.out.println("Before REFRESH: " + hsManaged.getHoTenHS());

            // REFRESH (đọc lại từ DB, bỏ thay đổi chưa flush trong PC nếu có)
            em.refresh(hsManaged);
            timeline.add("5) REFRESH: tên sau refresh = " + hsManaged.getHoTenHS());

            hsManaged.setHoTenHS("Name before remove (managed again)");

            // REMOVE → trạng thái DELETED trong PC (Hibernate: contains trả false)
            em.remove(hsManaged);
            timeline.add("6) REMOVED: em.contains(hsManaged) = " + em.contains(hsManaged));

            em.flush(); // DELETE
            timeline.add("6.1) FLUSH: DELETE đã được gửi xuống DB");

            // Re-create để có dữ liệu trả về
            HocSinh recreated = studentMapper.toEntity(dto);
            recreated.setHoTenHS("Final Name (re-created after delete)");
            em.persist(recreated); // INSERT sẽ diễn ra lúc commit hoặc flush
            timeline.add("7) RE-CREATED: em.contains(recreated) = " + em.contains(recreated));

            return ResponseData.builder()
                    .data(Map.of(
                            "student", studentMapper.toDto(recreated),
                            "lifecycleTimeline", timeline
                    ))
                    .message("Lifecycle demo executed successfully")
                    .status(HttpStatus.CREATED.value())
                    .build();

        } finally {
            // KHÔI PHỤC flush mode ban đầu để không ảnh hưởng nơi khác
            em.setFlushMode(prev);
        }
    }
}
