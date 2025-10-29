package com.example.nodo_springboot.service.impl;

import com.example.nodo_springboot.dto.StudentWithScoreReqDTO;
import com.example.nodo_springboot.service.AuditLogService;
import com.example.nodo_springboot.service.StudentProcessService;
import com.example.nodo_springboot.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentProcessServiceImpl implements StudentProcessService {

    private final StudentService studentService;
    private final AuditLogService auditLogService;
    private final NotificationService notificationService;

    public StudentProcessServiceImpl(StudentService studentService, AuditLogService auditLogService, NotificationService notificationService) {
        this.studentService = studentService;
        this.auditLogService = auditLogService;
        this.notificationService = notificationService;
    }


    @Override
    public void processStudentCreation(String caseNumber, StudentWithScoreReqDTO dto) {

        // Log the start of the process
        System.out.println("Starting student creation process for case: " + caseNumber);
        auditLogService.log("INFO", "Bắt đầu xử lý tạo HS: " + dto.getStudent().getMaHS(), dto.getStudent().getMaHS());

        try {
            // Nếu case 3: Lỗi trc khi vào Tx-Main (Lỗi trước khi lưu A và B)
            if("3".equals(caseNumber)){
                System.out.println("[MainFlow] !!! Ném lỗi nhân tạo - CASE 3 !!!");
                throw new RuntimeException("Lỗi trước khi lưu A và B");
            }

            // --- Gọi vào Service Nghiệp vụ ---
            // Spring Proxy được kích hoạt, Tx-Main (REQUIRED) bắt đầu từ đây
            studentService.createStudentWithTransaction(caseNumber, dto);
            // Nếu Tx-Main thành công (không ném Exception)
            // --- Log 2: Ghi log thành công (LUÔN LUÔN LƯU) ---
            // Gọi Tx-Log (2). Nó chạy, commit và kết thúc ngay.
            System.out.println("[MainFlow] Ghi log 'INFO' (lần 2 - thành công)");
            auditLogService.log("INFO", "Tạo HS thành công: " + dto.getStudent().getMaHS(), dto.getStudent().getMaHS());
        } catch (Exception e) {
            // Bắt lỗi từ Tx-Main (Case 1, 2) hoặc từ MainFlow (Case 3)
            System.out.println("[MainFlow] Đã bắt được lỗi: " + e.getMessage());

            // --- Log 3: Ghi log thất bại (LUÔN LUÔN LƯU) ---
            // Gọi Tx-Log (3). Nó chạy, commit và kết thúc ngay.
            System.out.println("[MainFlow] Ghi log 'ERROR' (lần 3 - thất bại)");
            auditLogService.log("ERROR", "Xử lý HS thất bại: " + dto.getStudent().getMaHS() + ". Lỗi: " + e.getMessage(), dto.getStudent().getMaHS());

            // Đây là ngữ cảnh KHÔNG có transaction (vì Tx-Main đã rollback/đóng)
            System.out.println("[MainFlow] Đang gọi NotificationService (hợp lệ)...");
            try {
                notificationService.sendNotification(dto.getStudent().getMaHS());
            } catch (Exception notifyEx) {
                System.err.println("Lỗi khi gửi thông báo: " + notifyEx.getMessage());
            }
        }
    }
}
