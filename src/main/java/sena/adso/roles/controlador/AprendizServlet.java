package sena.adso.roles.controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sena.adso.roles.dao.AprendizDAO;
import sena.adso.roles.modelo.Aprendiz;
import sena.adso.roles.modelo.Usuario;

/**
 * Servlet que maneja las operaciones CRUD para aprendices
 * Implementa diferentes comportamientos según el rol del usuario
 */
@WebServlet(name = "AprendizServlet", urlPatterns = {"/instructor/aprendices", "/aprendiz/aprendices"})
public class AprendizServlet extends HttpServlet {

    private AprendizDAO aprendizDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        aprendizDAO = new AprendizDAO();
    }

    /**
     * Maneja las solicitudes GET al servlet de aprendices
     * Muestra la lista de aprendices según el rol del usuario
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            accion = "listar";
        }
        
        switch (accion) {
            case "nuevo":
                if ("Instructor".equals(usuario.getRol())) {
                    mostrarFormulario(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
                }
                break;
            case "editar":
                if ("Instructor".equals(usuario.getRol())) {
                    cargarAprendiz(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
                }
                break;
            case "eliminar":
                if ("Instructor".equals(usuario.getRol())) {
                    eliminarAprendiz(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
                }
                break;
            default:
                listarAprendices(request, response, usuario.getRol());
                break;
        }
    }

    /**
     * Maneja las solicitudes POST al servlet de aprendices
     * Procesa los formularios de creación y edición de aprendices
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Solo los instructores pueden crear o editar aprendices
        if (!"Instructor".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/acceso-denegado.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");
        
        if ("guardar".equals(accion)) {
            guardarAprendiz(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/instructor/aprendices");
        }
    }
    
    /**
     * Muestra la lista de aprendices
     */
    private void listarAprendices(HttpServletRequest request, HttpServletResponse response, String rol) 
            throws ServletException, IOException {
        List<Aprendiz> aprendices = aprendizDAO.listarTodos();
        request.setAttribute("aprendices", aprendices);
        request.setAttribute("rol", rol);
        
        String vista = "Instructor".equals(rol) ? "/WEB-INF/instructor/aprendices.jsp" : "/WEB-INF/aprendiz/aprendices.jsp";
        request.getRequestDispatcher(vista).forward(request, response);
    }
    
    /**
     * Muestra el formulario para crear un nuevo aprendiz
     */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/instructor/formulario-aprendiz.jsp").forward(request, response);
    }
    
    /**
     * Carga los datos de un aprendiz para edición
     */
    private void cargarAprendiz(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Aprendiz aprendiz = aprendizDAO.buscarPorId(id);
            
            if (aprendiz != null) {
                request.setAttribute("aprendiz", aprendiz);
                request.getRequestDispatcher("/WEB-INF/instructor/formulario-aprendiz.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "Aprendiz no encontrado");
                request.setAttribute("tipo", "danger");
                listarAprendices(request, response, "Instructor");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "ID de aprendiz inválido");
            request.setAttribute("tipo", "danger");
            listarAprendices(request, response, "Instructor");
        }
    }
    
    /**
     * Guarda o actualiza un aprendiz
     */
    private void guardarAprendiz(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String email = request.getParameter("email");
        
        // Validar campos obligatorios
        if (cedula == null || cedula.isEmpty() || nombre == null || nombre.isEmpty() ||
                direccion == null || direccion.isEmpty() || email == null || email.isEmpty()) {
            request.setAttribute("mensaje", "Todos los campos son obligatorios");
            request.setAttribute("tipo", "danger");
            request.getRequestDispatcher("/WEB-INF/instructor/formulario-aprendiz.jsp").forward(request, response);
            return;
        }
        
        Aprendiz aprendiz = new Aprendiz();
        aprendiz.setCedula(cedula);
        aprendiz.setNombre(nombre);
        aprendiz.setDireccion(direccion);
        aprendiz.setEmail(email);
        
        boolean resultado;
        String mensaje;
        
        // Determinar si es inserción o actualización
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                aprendiz.setId(id);
                resultado = aprendizDAO.actualizar(aprendiz);
                mensaje = resultado ? "Aprendiz actualizado correctamente" : "Error al actualizar el aprendiz";
            } catch (NumberFormatException e) {
                resultado = false;
                mensaje = "ID de aprendiz inválido";
            }
        } else {
            // Verificar si ya existe la cédula o el email
            if (aprendizDAO.existeCedula(cedula)) {
                request.setAttribute("mensaje", "Ya existe un aprendiz con esa cédula");
                request.setAttribute("tipo", "danger");
                request.getRequestDispatcher("/WEB-INF/instructor/formulario-aprendiz.jsp").forward(request, response);
                return;
            }
            
            if (aprendizDAO.existeEmail(email)) {
                request.setAttribute("mensaje", "Ya existe un aprendiz con ese email");
                request.setAttribute("tipo", "danger");
                request.getRequestDispatcher("/WEB-INF/instructor/formulario-aprendiz.jsp").forward(request, response);
                return;
            }
            
            resultado = aprendizDAO.insertar(aprendiz);
            mensaje = resultado ? "Aprendiz registrado correctamente" : "Error al registrar el aprendiz";
        }
        
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipo", resultado ? "success" : "danger");
        listarAprendices(request, response, "Instructor");
    }
    
    /**
     * Elimina un aprendiz
     */
    private void eliminarAprendiz(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean resultado = aprendizDAO.eliminar(id);
            
            request.setAttribute("mensaje", resultado ? "Aprendiz eliminado correctamente" : "Error al eliminar el aprendiz");
            request.setAttribute("tipo", resultado ? "success" : "danger");
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "ID de aprendiz inválido");
            request.setAttribute("tipo", "danger");
        }
        
        listarAprendices(request, response, "Instructor");
    }
}