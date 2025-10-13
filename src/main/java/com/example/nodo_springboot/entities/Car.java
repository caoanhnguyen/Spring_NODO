package com.example.nodo_springboot.entities;

import com.example.nodo_springboot.Interface.Engine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Lazy
@Component
public class Car {
    @Autowired
    @Qualifier("VNEngine")
    private Engine engine;

    private String owner;

    public Car(Engine engine) {
        // Khi tạo Car thì gắn Engine vào
        this.engine = engine;
    }


    public Car() {
    }

    @PostConstruct
    public void run() {
        System.out.println("Car is running...");
    }

    @PreDestroy
    public void stop() {
        System.out.println("Car is stopping...");
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
