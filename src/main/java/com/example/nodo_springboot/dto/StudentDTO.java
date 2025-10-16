package com.example.nodo_springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class StudentDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public StudentDTO() {

    }

    public StudentDTO(Long id, String fullName, String email, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
