package com.training.spring.service.measure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.training.spring.service.measure", "com.training.spring.service.config"})
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties
public class MeasureServiceTestConfiguration {
}
