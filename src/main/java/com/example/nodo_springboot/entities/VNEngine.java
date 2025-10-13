package com.example.nodo_springboot.entities;

import com.example.nodo_springboot.Interface.Engine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class VNEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Vietnam engine started.");
    }
}
