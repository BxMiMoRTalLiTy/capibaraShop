/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;
import java.sql.*;
import javax.swing.JOptionPane;

public class UsuarioDAO {
    public Usuario login(String usuario, String contrasena) throws SQLException{
        String sql = "SELECT u.id_Usuario, u.usuarioNombre, u.contrasena, r.nombreRol "
                + "FROM Usuarios u "
                + "JOIN ROLES r ON u.id_rol = r.id_Rol "
                + "WHERE u.usuarioNombre = ? AND u.contrasena = ?";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    return new Usuario(
                            rs.getString("usuarioNombre"),
                            rs.getString("nombre"),
                            rs.getString("contrasena"),
                            rs.getString("email"),
                            rs.getDate("fechaNacimiento"),
                            rs.getString("tel"),
                            rs.getInt("id_rol")
                    );
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error en el Login: " + e.getMessage());
            }
        return null;
    }
    
    public boolean insertarUsuario(Usuario u) throws SQLException{ //usuarioNombre, nombre, contrasena, email, tel, id_rol
        String sql = "INSERT INTO Usuarios(usuarioNombre, nombre, contrasena, email, fechaNacimiento, tel, id_rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement insert = con.prepareStatement(sql)){
            
            insert.setString(1, u.getUsuarioNombre());
            insert.setString(2, u.getNombre());
            insert.setString(3, u.getContrasena());
            insert.setString(4, u.getEmail());
            insert.setDate(5, u.getFechaNacimiento());
            insert.setString(6, u.getTel());
            insert.setInt(7, u.getIdRol());
            
            return insert.executeUpdate() > 0;
        } catch(SQLException e){
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean usuarioExiste(String usuarioNombre){
        String sql = "SELECT 1 FROM Usuarios WHERE usuarioNombre = ?";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement insert = con.prepareStatement(sql)){
            insert.setString(1, usuarioNombre);
            
            try(ResultSet rs = insert.executeQuery()){
                return rs.next();
            }
        
        }catch (SQLException e) {
            System.out.println("Error al verificar existencia de usuario: " + e.getMessage());
            return true; // Por seguridad, bloquea si hay error
        }
    }
    
    public boolean emailExiste(String email){
        String sql = "SELECT 1 FROM Usuarios WHERE email = ?";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement insert = con.prepareStatement(sql)){
            insert.setString(1, email);
            
            try(ResultSet rs = insert.executeQuery()){
                return rs.next();
            }
        
        }catch (SQLException e) {
            System.out.println("Error al verificar existencia de email: " + e.getMessage());
            return true; // Por seguridad, bloquea si hay error
        }
    }
    
}
