package com.example.demo.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.usuario.Usuario;

// Interceptor: se ejecuta ANTES de cada controller protegido.
// Si no hay sesión, manda al login. Si el rol no corresponde, lo manda a su pantalla.
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String contexto = request.getContextPath();
        String ruta = request.getRequestURI().substring(contexto.length());
        Usuario usuario = (Usuario) request.getSession().getAttribute(Sesion.USUARIO);

        // Sin sesión: al login
        if (usuario == null) {
            response.sendRedirect(contexto + "/login");
            return false;
        }

        boolean esRutaMotorizado = ruta.startsWith("/motorizado");
        boolean esMotorizado = "MOTORIZADO".equals(usuario.getRol());

        // Motorizado intentando entrar a pantallas del admin
        if (esMotorizado && !esRutaMotorizado) {
            response.sendRedirect(contexto + "/motorizado/pedidos");
            return false;
        }
        // Admin intentando entrar a pantallas del motorizado
        if (!esMotorizado && esRutaMotorizado) {
            response.sendRedirect(contexto + "/admin");
            return false;
        }
        return true;
    }
}
