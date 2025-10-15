package com.example.nodo_springboot.dto;

import com.example.nodo_springboot.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

//@JsonIgnoreProperties(value = {"full_name"})
public class UserDTO extends AbstractDTO {

//    @JsonProperty("user_name")
//    @NotBlank(message = "Username is mandatory")
    private String usename;
    @JsonIgnore
    private String password;
    @JsonProperty("email_address")
    private String email;
    @JsonProperty("full_name")
    private String fullName;
//    @JsonProperty("status")
//    @JsonFormat(shape = JsonFormat.Shape.OBJECT, with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, pattern = "ACTIVE|INACTIVE|DELETED")
//    private Status status;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC+8")
//    @JsonProperty("create_at")
//    private Date createdAt;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC+8")
//    @JsonProperty("update_at")
//    @JsonIgnore
//    private Date updatedAt;

    public UserDTO() {

    }

    public UserDTO(String usename, String password, String email, String fullName, Date createdAt, Date updatedAt) {
        this.usename = usename;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
//        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "usename='" + usename + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                '}';
    }

    @JsonProperty("userName")
    public String getUsename() {
        return usename;
    }
    @JsonProperty("user_name")
    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }

}
