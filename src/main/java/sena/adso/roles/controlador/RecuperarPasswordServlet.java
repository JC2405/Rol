package sena.adso.roles.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sena.adso.roles.dao.UsuarioDAO;
import sena.adso.roles.modelo.Usuario;
import sena.adso.roles.util.EmailUtil;

/**
 * Servlet que maneja el proceso de recuperación de contraseña
 */
@WebServlet(name = "RecuperarPasswordServlet", urlPatterns = {"/recuperar-password"})
public class RecuperarPasswordServlet extends HttpServlet {

    /**
     * Maneja las solicitudes GET al servlet de recuperación
     * Muestra el formulario de recuperación de contraseña
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/recuperar-password.jsp").forward(request, response);
    }

    /**
     * Maneja las solicitudes POST al servlet de recuperación
     * Procesa el formulario de recuperación de contraseña
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String mensaje = "";
        
        // Validar que se haya proporcionado un email
        if (email == null || email.isEmpty()) {
            mensaje = "Por favor, ingrese su dirección de correo electrónico";
            request.setAttribute("mensaje", mensaje);
            request.getRequestDispatcher("/recuperar-password.jsp").forward(request, response);
            return;
        }
        
        // Buscar el usuario por email
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        
        if (usuario != null) {
            // Generar nueva contraseña temporal
            String nuevaPassword = EmailUtil.generarPasswordTemporal();
            
            // Actualizar la contraseña en la base de datos y marcarla explícitamente como temporal
            boolean actualizado = usuarioDAO.actualizarPassword(email, nuevaPassword, true); // true = es temporal
            
            System.out.println("Contraseña temporal generada para " + email + ": " + nuevaPassword);
            System.out.println("¿Contraseña marcada como temporal? " + (actualizado ? "Sí" : "No"));
            
            if (actualizado) {
                // Enviar correo con la nueva contraseña
                boolean enviado = EmailUtil.enviarCorreoRecuperacion(email, nuevaPassword);
                
                if (enviado) {
                    mensaje = "Se ha enviado un correo con su nueva contraseña";
                    request.setAttribute("tipo", "success");
                } else {
                    mensaje = "Error al enviar el correo. Por favor, contacte al administrador";
                    request.setAttribute("tipo", "danger");
                }
            } else {
                mensaje = "Error al actualizar la contraseña. Por favor, intente nuevamente";
                request.setAttribute("tipo", "danger");
            }
        } else {
            // No mostrar si el email existe o no por seguridad
            mensaje = "Si su correo está registrado, recibirá instrucciones para recuperar su contraseña";
            request.setAttribute("tipo", "info");
        }
        
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/recuperar-password.jsp").forward(request, response);
    }
}