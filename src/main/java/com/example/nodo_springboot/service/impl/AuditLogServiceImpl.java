package com.example.nodo_springboot.service.impl;

import com.example.nodo_springboot.entities.AuditLog;
import com.example.nodo_springboot.repository.AuditLogRepository;
import com.example.nodo_springboot.service.AuditLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepo;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepo) {
        this.auditLogRepo = auditLogRepo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String level, String message, String contextMaHS) {
        try {
            AuditLog log = new AuditLog(level, message, contextMaHS);
            auditLogRepo.save(log);
            // Ngay khi phương thức này kết thúc, Tx-Log sẽ COMMIT
        } catch (Exception e) {
            // Trường hợp hy hữu: Nếu chính việc ghi log bị lỗi
            // (ví dụ: database log bị down), chúng ta không muốn nó
            // ảnh hưởng đến luồng chính
            System.err.println("LỖI NGHIÊM TRỌNG: KHÔNG THỂ GHI LOG: " + e.getMessage());
        }
    }

}
