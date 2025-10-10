package com.example.nodo_springboot;

import com.example.nodo_springboot.Config.AppConfig;
import com.example.nodo_springboot.entities.Address;
import com.example.nodo_springboot.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class NodoSpringbootApplication {

    public static void main(String[] args) {

        // Khởi tạo thủ công không dùng IoC
//        Address address = new Address(1L, "123 Main St", "Springfield", "IL", "62701");
//        User user = new User(1L, "John Doe", "a@gmail.com", address);

        // Khởi tạo dùng IoC
        ApplicationContext context = new AnnotationConfigApplicationContext(NodoSpringbootApplication.class);
        User user = context.getBean(User.class);
        System.out.println(user.getAddress().getCity()); // Sẽ in ra "Ha Noi "
    }

}
