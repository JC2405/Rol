package sena.adso.roles.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sena.adso.roles.modelo.Usuario;

/**
 * Filtro de seguridad para controlar el acceso a las páginas protegidas
 * según el rol del usuario autenticado
 */
@WebFilter(filterName = "SeguridadFiltro", urlPatterns = {"/instructor/*", "/aprendiz/*"})
public class SeguridadFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se requiere inicialización especial
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        
        // Verificar si el usuario está autenticado
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        boolean isInstructorArea = requestURI.contains("/instructor/");
        boolean isAprendizArea = requestURI.contains("/aprendiz/");
        
        if (isLoggedIn) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            String rol = usuario.getRol();
            
            // Verificar acceso según el rol
            if (isInstructorArea && !"Instructor".equals(rol)) {
                // Redirigir a la página de acceso denegado si un Aprendiz intenta acceder al área de Instructor
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/acceso-denegado.jsp");
                return;
            } else if (isAprendizArea && !"Aprendiz".equals(rol) && !"Instructor".equals(rol)) {
                // Los instructores también pueden acceder al área de aprendices
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/acceso-denegado.jsp");
                return;
            }
            
            // Si pasa las verificaciones, continuar con la cadena de filtros
            chain.doFilter(request, response);
        } else {
            // Si no está autenticado, redirigir al login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // No se requiere limpieza especial
    }
}