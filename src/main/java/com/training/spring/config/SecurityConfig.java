package com.training.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfig(DataSource dataSource) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers("/css/**", "/img/**").permitAll()
                        .anyRequest().authenticated()
                        .and().formLogin().loginPage("/formLogin").loginProcessingUrl("/login")
                        .usernameParameter("username").passwordParameter("password").permitAll()
                        .and().logout().invalidateHttpSession(true)
                        .and().csrf().disable();
            }
        };
    }
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/formLogin").setViewName("login");
            }
        };
    }
}