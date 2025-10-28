package com.example.nodo_springboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "AUDIT_LOG")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "LogLevel", length = 10) // "INFO", "ERROR"
    private String logLevel;

    @Column(name = "Message", length = 1000)
    private String message;

    @Column(name = "ContextMaHS", length = 10) // Lưu lại MaHS để dễ truy vết
    private String contextMaHS;

    public AuditLog(String logLevel, String message, String contextMaHS) {
        this.timestamp = LocalDateTime.now();
        this.logLevel = logLevel;
        this.message = message;
        this.contextMaHS = contextMaHS;
    }
}