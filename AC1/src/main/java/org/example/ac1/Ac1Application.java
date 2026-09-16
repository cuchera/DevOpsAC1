package org.example.ac1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"entity"})
public class Ac1Application {

    public static void main(String[] args) {
        SpringApplication.run(Ac1Application.class, args);
    }

}
