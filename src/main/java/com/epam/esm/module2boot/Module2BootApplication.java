package com.epam.esm.module2boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Module2BootApplication {

    public static void main(String[] args) {

        //  SpringApplication.run(Module2BootApplication.class, args);

        SpringApplication app = new SpringApplication(Module2BootApplication.class);
        Environment env = app.run(args).getEnvironment();
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile : activeProfiles) {
            System.out.println("Active profile: " + profile);
        }

    }

}
