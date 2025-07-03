package com.dmancloud.dinesh.demoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoappApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoappApplication.class, args);
    }
    public void addNuber(int x, int y){
        int z= x+y;
    }
      public static void useHardcodedCredentials() {
        String password = "admin123"; // hardcoded password
        System.out.println("Using hardcoded password: " + password);
    }
}
