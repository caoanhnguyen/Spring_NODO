package com.example.nodo_springboot.service;

public interface AuditLogService {
    void log(String level, String message, String contextMaHS);
}
