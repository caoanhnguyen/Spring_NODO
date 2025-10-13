package com.example.nodo_springboot.entities;

import com.example.nodo_springboot.Interface.Engine;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ChinaEngine implements Engine {
    @Override
    public void start() {
        System.out.println("China engine started.");
    }

}
