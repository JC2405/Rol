package sena.adso.roles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sena.adso.roles.modelo.Aprendiz;
import sena.adso.roles.util.ConexionBD;

/**
 * Clase DAO para gestionar las operaciones CRUD de aprendices en la base de datos
 */
public class AprendizDAO {
    
    /**
     * Obtiene todos los aprendices registrados en la base de datos
     * @return Lista de objetos Aprendiz
     */
    public List<Aprendiz> listarTodos() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Aprendiz> listaAprendices = new ArrayList<>();
        
        try {
            conn = ConexionBD.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aprendices ORDER BY nombre");
            
            while (rs.next()) {
                Aprendiz aprendiz = new Aprendiz();
                aprendiz.setId(rs.getInt("id"));
                aprendiz.setCedula(rs.getString("cedula"));
                aprendiz.setNombre(rs.getString("nombre"));
                aprendiz.setDireccion(rs.getString("direccion"));
                aprendiz.setEmail(rs.getString("email"));
                aprendiz.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                listaAprendices.add(aprendiz);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar aprendices: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return listaAprendices;
    }
    
    /**
     * Busca un aprendiz por su ID
     * @param id ID del aprendiz a buscar
     * @return Objeto Aprendiz si se encuentra, null en caso contrario
     */
    public Aprendiz buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aprendiz aprendiz = null;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "SELECT * FROM aprendices WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                aprendiz = new Aprendiz();
                aprendiz.setId(rs.getInt("id"));
                aprendiz.setCedula(rs.getString("cedula"));
                aprendiz.setNombre(rs.getString("nombre"));
                aprendiz.setDireccion(rs.getString("direccion"));
                aprendiz.setEmail(rs.getString("email"));
                aprendiz.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar aprendiz por ID: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return aprendiz;
    }
    
    /**
     * Inserta un nuevo aprendiz en la base de datos
     * @param aprendiz Objeto Aprendiz con los datos a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insertar(Aprendiz aprendiz) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "INSERT INTO aprendices (cedula, nombre, direccion, email) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aprendiz.getCedula());
            stmt.setString(2, aprendiz.getNombre());
            stmt.setString(3, aprendiz.getDireccion());
            stmt.setString(4, aprendiz.getEmail());
            
            int filasAfectadas = stmt.executeUpdate();
            resultado = (filasAfectadas > 0);
        } catch (SQLException ex) {
            System.out.println("Error al insertar aprendiz: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return resultado;
    }
    
    /**
     * Actualiza los datos de un aprendiz existente
     * @param aprendiz Objeto Aprendiz con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Aprendiz aprendiz) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "UPDATE aprendices SET cedula = ?, nombre = ?, direccion = ?, email = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aprendiz.getCedula());
            stmt.setString(2, aprendiz.getNombre());
            stmt.setString(3, aprendiz.getDireccion());
            stmt.setString(4, aprendiz.getEmail());
            stmt.setInt(5, aprendiz.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            resultado = (filasAfectadas > 0);
        } catch (SQLException ex) {
            System.out.println("Error al actualizar aprendiz: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return resultado;
    }
    
    /**
     * Elimina un aprendiz de la base de datos
     * @param id ID del aprendiz a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean resultado = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "DELETE FROM aprendices WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            resultado = (filasAfectadas > 0);
        } catch (SQLException ex) {
            System.out.println("Error al eliminar aprendiz: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return resultado;
    }
    
    /**
     * Verifica si ya existe un aprendiz con la cédula especificada
     * @param cedula Cédula a verificar
     * @return true si ya existe, false en caso contrario
     */
    public boolean existeCedula(String cedula) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "SELECT COUNT(*) FROM aprendices WHERE cedula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cedula);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                existe = (rs.getInt(1) > 0);
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar cédula: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return existe;
    }
    
    /**
     * Verifica si ya existe un aprendiz con el email especificado
     * @param email Email a verificar
     * @return true si ya existe, false en caso contrario
     */
    public boolean existeEmail(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;
        
        try {
            conn = ConexionBD.getConnection();
            String sql = "SELECT COUNT(*) FROM aprendices WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                existe = (rs.getInt(1) > 0);
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar email: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) ConexionBD.closeConnection(conn);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        
        return existe;
    }
}