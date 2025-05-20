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
 * Servlet que maneja el proceso de registro de usuarios
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    /**
     * Maneja las solicitudes GET al servlet de registro
     * Muestra el formulario de registro
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }

    /**
     * Maneja las solicitudes POST al servlet de registro
     * Procesa el formulario de registro de usuario
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");
        String mensaje = "";
        
        // Validar que se hayan proporcionado todos los campos
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || rol == null || rol.isEmpty()) {
            mensaje = "Por favor, complete todos los campos del formulario";
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipo", "danger");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }
        
        // Verificar que el rol sea válido
        if (!rol.equals("Instructor") && !rol.equals("Aprendiz")) {
            mensaje = "El rol seleccionado no es válido";
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipo", "danger");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }
        
        // Verificar si el usuario ya existe
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.existeUsername(username)) {
            mensaje = "El nombre de usuario ya está en uso. Por favor, elija otro";
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipo", "danger");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }
        
        // Verificar si el email ya existe
        Usuario usuarioExistente = usuarioDAO.buscarPorEmail(email);
        if (usuarioExistente != null) {
            mensaje = "El correo electrónico ya está registrado";
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tipo", "danger");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }
        
        // Generar contraseña aleatoria
        String password = EmailUtil.generarPasswordTemporal();
        
        // Crear objeto Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setRol(rol);
        
        // Registrar usuario en la base de datos
        boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario);
        
        if (registrado) {
            // Enviar credenciales por correo electrónico
            boolean enviado = EmailUtil.enviarCredencialesRegistro(email, username, password);
            
            if (enviado) {
                mensaje = "Registro exitoso. Se han enviado las credenciales a su correo electrónico";
                request.setAttribute("tipo", "success");
            } else {
                mensaje = "Registro exitoso, pero hubo un problema al enviar las credenciales por correo";
                request.setAttribute("tipo", "warning");
            }
        } else {
            mensaje = "Error al registrar el usuario. Por favor, intente nuevamente";
            request.setAttribute("tipo", "danger");
        }
        
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/registro.jsp").forward(request, response);
    }
}