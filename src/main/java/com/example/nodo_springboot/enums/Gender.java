package com.example.nodo_springboot.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("Nam")
    MALE,
    @JsonProperty("Nữ")
    FEMALE,
    @JsonProperty("Khác")
    OTHER
}
