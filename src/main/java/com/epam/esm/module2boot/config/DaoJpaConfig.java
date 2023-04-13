package com.epam.esm.module2boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@Profile("jpa")
public class DaoJpaConfig  {


}
