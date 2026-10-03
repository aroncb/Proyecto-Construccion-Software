package com.example.demo.login;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Registra el interceptor en las rutas que necesitan sesión.
// Las rutas del cliente (/catalogo, /pedido/**) y /login quedan libres.
@Configuration
public class SeguridadConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public SeguridadConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                        "/admin", "/admin/**",
                        "/categoria/**",
                        "/producto/**",
                        "/usuario/**",
                        "/motorizado/**");
    }
}
