package com.shank.Blogify.security;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ServletRegistration() {
        return new ServletRegistrationBean<>(
            new JakartaWebServlet(),
            "/console/*"
        );
    }
}