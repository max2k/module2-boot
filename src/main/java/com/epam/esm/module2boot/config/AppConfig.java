package com.epam.esm.module2boot.config;

import com.epam.esm.module2boot.validator.GiftCertificateQueryDTOValidator;
import com.epam.esm.module2boot.validator.impl.GiftCertificateQueryDTOValidatorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm")
public class AppConfig implements WebMvcConfigurer {

    private final DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    GiftCertificateQueryDTOValidator giftCertificateQueryDTOValidator(){
        return new GiftCertificateQueryDTOValidatorImpl();
    }

}
