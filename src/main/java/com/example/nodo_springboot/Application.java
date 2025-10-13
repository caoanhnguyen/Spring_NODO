package com.example.nodo_springboot;

import com.example.nodo_springboot.Interface.Engine;
import com.example.nodo_springboot.entities.Car;
import com.example.nodo_springboot.entities.ChinaEngine;
import com.example.nodo_springboot.entities.VNEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        Engine engine = new VNEngine();
        Engine engine2 = new ChinaEngine();

        ApplicationContext context = SpringApplication.run(Application.class, args);

        Car car = context.getBean(Car.class);
//        car.setEngine(engine2);
//        Car car = new Car();
//        car.setEngine(engine);



        car.getEngine().start();


        System.out.println("Type 'quit' to exit...");
        while (true) {
            String input = sc.nextLine();
            if ("quit".equalsIgnoreCase(input.trim())) {
                break;
            }
        }
        System.out.println("Application is shutting down...");
        sc.close();
    }

}
