package com.example.nodo_springboot.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {
    /**
     * Phương thức này CẤM chạy bên trong transaction.
     * Dùng để mô phỏng việc gọi API, gửi email...
     */
    @Transactional(propagation = Propagation.NEVER)
    public void sendNotification(String maHS) {
        System.out.println("[Tx-NEVER] Đang chuẩn bị gửi thông báo cho " + maHS);
        try {
            Thread.sleep(50); // Dộ trễ mạng
        } catch (InterruptedException e) {
            // ...
        }
        System.out.println("[Tx-NEVER] Gửi thông báo thành công!");
    }
}
