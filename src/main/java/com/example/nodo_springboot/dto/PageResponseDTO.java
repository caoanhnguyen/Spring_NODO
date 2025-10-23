package com.example.nodo_springboot.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PageResponseDTO<T> implements Serializable {
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private T items;
}
