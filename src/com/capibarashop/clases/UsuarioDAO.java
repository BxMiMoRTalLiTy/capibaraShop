/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {
    
    private static final String CAMPOS_USUARIO_CON_ROL = """
            u.id_Usuario, u.usuarioNombre, u.nombre, u.contrasena, u.email, u.fechaNacimiento, u.tel, u.id_rol, r.nombreRol
        """;
    
    public Usuario construirUsuario(ResultSet rs) throws SQLException{
        Usuario u = new Usuario();
        
        u.setId(rs.getInt("id_Usuario"));
        u.setUsuarioNombre(rs.getString("usuarioNombre"));
        u.setNombre(rs.getString("nombre"));
        u.setContrasena(rs.getString("contrasena"));
        u.setEmail(rs.getString("email"));
        u.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        u.setTel(rs.getString("tel"));

        Rol rol = new Rol(rs.getInt("id_Rol"), rs.getString("nombreRol"));
        u.setRol(rol);

        return u;
    }
    
    public Usuario login(String usuario, String contrasena) throws SQLException{
        String sql = "SELECT " + CAMPOS_USUARIO_CON_ROL
            + "FROM Usuarios u "
            + "JOIN ROLES r ON u.id_rol = r.id_Rol "
            + "WHERE u.usuarioNombre = ? AND u.contrasena = ?";

        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    return construirUsuario(rs);
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Hubo un error al guardar.", JOptionPane.ERROR_MESSAGE);
            }
        return null;
    }
    
    public boolean insertarUsuario(Usuario u) throws SQLException{ //usuarioNombre, nombre, contrasena, email, tel, id_rol
        String sql = "INSERT INTO Usuarios(usuarioNombre, nombre, contrasena, email, fechaNacimiento, tel, id_rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, u.getUsuarioNombre());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getEmail());
            ps.setDate(5, u.getFechaNacimiento());
            ps.setString(6, u.getTel());
            ps.setInt(7, u.getRol().getId());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al insertar el usuario" + e.getMessage(), "Hubo un error al guardar", 
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean usuarioExiste(String usuarioNombre){
        String sql = "SELECT 1 FROM Usuarios WHERE usuarioNombre = ?";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, usuarioNombre);
            
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        
        }catch (SQLException e) {
            System.out.println("Error al verificar existencia de usuario: " + e.getMessage());
            return true; // Por seguridad, bloquea si hay error
        }
    }
    
    public Usuario buscarUsuarioID(int id){
        String sql = "SELECT " + CAMPOS_USUARIO_CON_ROL
            + "FROM Usuarios u "
            + "JOIN ROLES r ON u.id_rol = r.id_Rol "
            + "WHERE u.id_Usuario = ?";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    return construirUsuario(rs);
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Hubo un error al guardar.", JOptionPane.ERROR_MESSAGE);
            }
        return null;
    }
    
    public boolean emailExiste(String email){
        String sql = "SELECT 1 FROM Usuarios WHERE email = ?";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, email);
            
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        
        }catch (SQLException e) {
            System.out.println("Error al verificar existencia de email: " + e.getMessage());
            return true; // Por seguridad, bloquea si hay error
        }
    }
    
    public List<Rol> obtenerRoles() throws SQLException{
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_Rol, nombreRol FROM Roles "
                + "WHERE nombreRol <> 'Administrador'";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Rol rol = new Rol(rs.getInt("id_Rol"), rs.getString("nombreRol"));
                lista.add(rol);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return lista;
    }
    
}
